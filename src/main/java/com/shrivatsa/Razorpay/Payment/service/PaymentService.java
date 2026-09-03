package com.shrivatsa.Razorpay.Payment.service;

import com.shrivatsa.Razorpay.Payment.dto.request.PaymentInitRequest;
import com.shrivatsa.Razorpay.Payment.dto.response.PaymentResponse;

import java.util.UUID;

public interface PaymentService {
    PaymentResponse initiate(UUID merchantId, PaymentInitRequest request);
    PaymentResponse capture(UUID merchantId, UUID paymentId);

    void resolveAuthorization(UUID paymentId, boolean approve, String bankRef, String errorCode, String errorDescription);
}
