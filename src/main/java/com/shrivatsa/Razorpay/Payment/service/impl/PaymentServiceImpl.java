package com.shrivatsa.Razorpay.Payment.service.impl;

import com.shrivatsa.Razorpay.Payment.dto.request.PaymentInitRequest;
import com.shrivatsa.Razorpay.Payment.dto.response.PaymentResponse;
import com.shrivatsa.Razorpay.Payment.entity.OrderRecord;
import com.shrivatsa.Razorpay.Payment.entity.Payment;
import com.shrivatsa.Razorpay.Payment.gateway.PaymentGatewayRouter;
import com.shrivatsa.Razorpay.Payment.gateway.dto.PaymentRequest;
import com.shrivatsa.Razorpay.Payment.gateway.dto.PaymentResult;
import com.shrivatsa.Razorpay.Payment.mapper.PaymentMapper;
import com.shrivatsa.Razorpay.Payment.repository.OrderRepository;
import com.shrivatsa.Razorpay.Payment.repository.PaymentRepository;
import com.shrivatsa.Razorpay.Payment.service.PaymentService;
import com.shrivatsa.Razorpay.common.enums.OrderStatus;
import com.shrivatsa.Razorpay.common.enums.PaymentStatus;
import com.shrivatsa.Razorpay.common.exception.BusinessRuleViolationException;
import com.shrivatsa.Razorpay.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;
@Service
@RequiredArgsConstructor
@Slf4j

@Transactional
public class PaymentServiceImpl implements PaymentService {
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentGatewayRouter  paymentGatewayRouter;
    private final PaymentMapper paymentMapper;
    @Override
    public PaymentResponse initiate(UUID merchantId, PaymentInitRequest request) {
        OrderRecord order = orderRepository.findByIdAndMerchantId(request.orderId(),merchantId)
                .orElseThrow(()-> new ResourceNotFoundException("Order",request.orderId()));

        if(order.getOrderStatus()!= OrderStatus.CREATED &&  order.getOrderStatus()!= OrderStatus.ATTEMPTED){
            throw new BusinessRuleViolationException("ORDER_NOT_PAYBLE","Order cannot accept payment in status :"+order.getOrderStatus());
        }
        order.setOrderStatus(OrderStatus.ATTEMPTED);
        order.setAttempts(order.getAttempts()+1);

        Payment payment = Payment.builder()
                .order(order)
                .merchantId(merchantId)
                .amount(order.getAmount())
                .status(PaymentStatus.CREATED)
                .method(request.method())
                .methodDetails(request.methodDetails())
                .build();
        payment = paymentRepository.save(payment);

        PaymentRequest paymentRequest = new PaymentRequest(payment.getId(),
                request.orderId(), merchantId,
                order.getAmount(), request.method(),
                request.methodDetails());
        PaymentResult result = paymentGatewayRouter.initiate(paymentRequest);

        switch (result) {
            case PaymentResult.Pending pending -> payment.setProcessorReference(pending.registrationRef());
            case PaymentResult.Failure failure -> {
                payment.setStatus(PaymentStatus.FAILED);
                payment.setErrorCode(failure.errorCode());
                payment.setErrorDescription(failure.errorDescription());
            }
            case PaymentResult.Success success->{}
        }

        payment = paymentRepository.save(payment);
        orderRepository.save(order);

        return paymentMapper.toResponse(payment);
    }
}
