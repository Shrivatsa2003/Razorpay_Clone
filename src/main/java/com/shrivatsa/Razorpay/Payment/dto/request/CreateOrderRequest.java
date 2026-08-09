package com.shrivatsa.Razorpay.Payment.dto.request;

import com.shrivatsa.Razorpay.common.entity.Money;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.Map;

public record CreateOrderRequest(
        @NotNull
        Money amount,
        @Size(max=100)
        String receipt,

        Map<String,Object> notes,

        LocalDateTime expiresAt

) {
}
