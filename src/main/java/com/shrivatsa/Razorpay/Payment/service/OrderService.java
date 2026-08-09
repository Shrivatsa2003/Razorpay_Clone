package com.shrivatsa.Razorpay.Payment.service;

import com.shrivatsa.Razorpay.Payment.dto.request.CreateOrderRequest;
import com.shrivatsa.Razorpay.Payment.dto.response.OrderResponse;
import com.shrivatsa.Razorpay.Payment.dto.response.PaymentResponse;
import jakarta.annotation.Nullable;

import java.util.List;
import java.util.UUID;

public interface OrderService {

   OrderResponse create(UUID merchantId , CreateOrderRequest request);
   OrderResponse getById(UUID merchantId , UUID orderId);
   OrderResponse cancel(UUID merchantId , UUID orderId);
   List<PaymentResponse> listPayments(UUID merchantId , UUID orderId);
}
