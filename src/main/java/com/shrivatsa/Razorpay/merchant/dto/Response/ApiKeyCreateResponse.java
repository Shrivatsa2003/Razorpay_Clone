package com.shrivatsa.Razorpay.merchant.dto.Response;

import com.shrivatsa.Razorpay.common.enums.Environment;

import java.util.UUID;

public record ApiKeyCreateResponse(
        UUID id,
        String keyId,
        String keySecret,
        Environment environment
) {
}
