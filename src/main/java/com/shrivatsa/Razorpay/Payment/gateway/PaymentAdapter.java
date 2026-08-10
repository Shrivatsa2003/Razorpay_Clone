package com.shrivatsa.Razorpay.Payment.gateway;

import com.shrivatsa.Razorpay.Payment.gateway.dto.PaymentRequest;
import com.shrivatsa.Razorpay.Payment.gateway.dto.PaymentResult;

public interface PaymentAdapter {
    PaymentResult initiate(PaymentRequest request);
}
