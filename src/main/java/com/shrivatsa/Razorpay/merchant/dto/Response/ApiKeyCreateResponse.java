package com.shrivatsa.Razorpay.merchant.dto.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shrivatsa.Razorpay.common.enums.Environment;

import java.util.UUID;

public record ApiKeyCreateResponse(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("keyId")
        String keyId,
        @JsonProperty("keySecret")
        String keySecret,
        @JsonProperty("environment")
        Environment environment
) {
}
