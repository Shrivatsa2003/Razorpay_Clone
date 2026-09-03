package com.shrivatsa.Razorpay.vault.dto.response;


import com.shrivatsa.Razorpay.common.enums.CardBrand;

public record TokenizeResponse(
        String token,
        String lastFour,
        CardBrand brand,
        Integer expiryMonth,
        Integer expiryYear
) {
}
