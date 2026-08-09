package com.shrivatsa.Razorpay.merchant.dto.Response;

import com.shrivatsa.Razorpay.common.enums.BusinessType;
import com.shrivatsa.Razorpay.common.enums.MerchantStatus;

import java.util.UUID;

public record MerchantResponse(
        UUID id,
        String name,
        String email,
        String businessName,
        BusinessType businessType,
        MerchantStatus merchantStatus
) {
}
