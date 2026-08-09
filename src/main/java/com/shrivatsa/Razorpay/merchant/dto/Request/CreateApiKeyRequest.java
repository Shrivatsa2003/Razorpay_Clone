package com.shrivatsa.Razorpay.merchant.dto.Request;

import com.shrivatsa.Razorpay.common.enums.Environment;

public record CreateApiKeyRequest(
        Environment environment
) {
}
