package com.shrivatsa.Razorpay.Payment.service;

import com.shrivatsa.Razorpay.Payment.dto.request.PaymentInitRequest;
import com.shrivatsa.Razorpay.Payment.dto.response.PaymentResponse;

import java.util.UUID;

public interface PaymentService {
    PaymentResponse initiate(UUID merchantId, PaymentInitRequest request);
}
