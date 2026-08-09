package com.shrivatsa.Razorpay.Payment.service.impl;

import com.shrivatsa.Razorpay.Payment.dto.request.CreateOrderRequest;
import com.shrivatsa.Razorpay.Payment.dto.response.OrderResponse;
import com.shrivatsa.Razorpay.Payment.dto.response.PaymentResponse;
import com.shrivatsa.Razorpay.Payment.entity.OrderRecord;
import com.shrivatsa.Razorpay.Payment.entity.Payment;
import com.shrivatsa.Razorpay.Payment.mapper.OrderMapper;
import com.shrivatsa.Razorpay.Payment.mapper.PaymentMapper;
import com.shrivatsa.Razorpay.Payment.repository.OrderRepository;
import com.shrivatsa.Razorpay.Payment.repository.PaymentRepository;
import com.shrivatsa.Razorpay.Payment.service.OrderService;
import com.shrivatsa.Razorpay.common.enums.OrderStatus;
import com.shrivatsa.Razorpay.common.exception.BusinessRuleViolationException;
import com.shrivatsa.Razorpay.common.exception.DuplicateResourceException;
import com.shrivatsa.Razorpay.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final OrderMapper orderMapper;

    @Value("${payment.order.default-order-expiry-minutes:30}")
    private int defaultOrderExpiryMinutes;

    @Override
    @Transactional
    public OrderResponse create(UUID merchantId, CreateOrderRequest request) {
        if (request.receipt() != null && orderRepository.existsByMerchantIdAndReceipt(merchantId, request.receipt())) {
            throw new DuplicateResourceException("ORDER_RECEIPT_DUPLICATE", "Order with receipt already exists: " + request.receipt());
        }

        OrderRecord order = OrderRecord.builder()
                .receipt(request.receipt())
                .amount(request.amount())
                .notes(request.notes())

                .merchantId(merchantId)
                .orderStatus(OrderStatus.CREATED)
                .expiresAt(request.expiresAt() != null ? request.expiresAt() :
                        LocalDateTime.now().plusMinutes(defaultOrderExpiryMinutes))
                .build();

        order = orderRepository.save(order);

// TODO:        publish kafka event about order creation

        return orderMapper.toResponse(order);
    }

    @Override
    public OrderResponse getById(UUID merchantId, UUID orderId) {
        OrderRecord order = orderRepository.findByIdAndMerchantId(orderId, merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", orderId));
        return orderMapper.toResponse(order);
    }

    @Override
    @Transactional
    public OrderResponse cancel(UUID merchantId, UUID orderId) {
        OrderRecord order = orderRepository.findByIdAndMerchantId(orderId, merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", orderId));

        if(order.getOrderStatus() == OrderStatus.CANCELLED || order.getOrderStatus() == OrderStatus.PAID) {
            throw new BusinessRuleViolationException("ORDER_CANNOT_CANCEL",
                    "Cannot cancel order with status: "+order.getOrderStatus().name());
        }

        order.setOrderStatus(OrderStatus.CANCELLED);
        order = orderRepository.save(order);

        return orderMapper.toResponse(order);
    }

    @Override
    public List<PaymentResponse> listPayments(UUID merchantId, UUID orderId) {
        OrderRecord order = orderRepository.findByIdAndMerchantId(orderId, merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", orderId));

        List<Payment> paymentList = paymentRepository.findByOrder_Id(order);

//        return paymentList.stream().map(
//                payment -> paymentMapper.toResponse(payment)
//        ).collect(Collectors.toList());

        return paymentMapper.toResponseList(paymentList);
    }
}
