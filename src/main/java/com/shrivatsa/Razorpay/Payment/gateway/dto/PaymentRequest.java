package com.shrivatsa.Razorpay.Payment.gateway.dto;

import com.shrivatsa.Razorpay.common.entity.Money;
import com.shrivatsa.Razorpay.common.enums.PaymentMethod;

import java.util.Map;
import java.util.UUID;

public record PaymentRequest(
        UUID paymentId,
        UUID orderId,
        UUID merchantId,
        Money amount,
        PaymentMethod method,
        Map<String, Object> methodDetails
) {
}
