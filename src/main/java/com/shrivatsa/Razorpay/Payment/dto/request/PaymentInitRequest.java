package com.shrivatsa.Razorpay.Payment.dto.request;

import java.util.UUID;

public record PaymentInitRequest(
        UUID order
) {
}
