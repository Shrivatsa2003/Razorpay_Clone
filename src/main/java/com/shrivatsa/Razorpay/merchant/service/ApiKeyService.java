package com.shrivatsa.Razorpay.merchant.service;

import com.shrivatsa.Razorpay.merchant.dto.Request.CreateApiKeyRequest;
import com.shrivatsa.Razorpay.merchant.dto.Response.ApiKeyCreateResponse;
import com.shrivatsa.Razorpay.merchant.dto.Response.ApiKeyResponse;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public interface ApiKeyService {
    ApiKeyCreateResponse create(UUID merchantId,CreateApiKeyRequest request);

    List<ApiKeyResponse> listByMerchant(UUID merchantId);

    void revoke(UUID merchantId, UUID keyId);

    @Nullable ApiKeyCreateResponse rotate(UUID merchantId, UUID keyId);
}
