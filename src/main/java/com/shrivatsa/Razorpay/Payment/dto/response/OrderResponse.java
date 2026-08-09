package com.shrivatsa.Razorpay.Payment.dto.response;

import com.shrivatsa.Razorpay.common.entity.Money;
import com.shrivatsa.Razorpay.common.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public record OrderResponse(
        UUID id,
        UUID merchantId,
        String receipt,
        Money amount,
        OrderStatus status,
        Integer attempts,
        Map<String, Object> notes,
        LocalDateTime expiresAt,
        LocalDateTime createdAt
) {
}
