package com.shrivatsa.Razorpay.vault.service;

import com.shrivatsa.Razorpay.Payment.processor.dto.PaymentProcessorResponse;
import com.shrivatsa.Razorpay.common.entity.Money;
import com.shrivatsa.Razorpay.vault.dto.request.TokenizeRequest;
import com.shrivatsa.Razorpay.vault.dto.response.TokenizeResponse;

import java.util.Map;
import java.util.UUID;

public interface VaultService {
    TokenizeResponse tokenize(TokenizeRequest request, UUID merchantId);

    PaymentProcessorResponse charge(UUID paymentId, String token, Money amount, Map<String, Object> methodDetails);
}

