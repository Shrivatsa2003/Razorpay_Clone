package com.shrivatsa.Razorpay.merchant.mapper;


import com.shrivatsa.Razorpay.merchant.dto.Request.MerchantSignupRequest;
import com.shrivatsa.Razorpay.merchant.dto.Response.MerchantResponse;
import com.shrivatsa.Razorpay.merchant.entity.Merchant;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MerchantMapper {

    Merchant toEntityFromSignUpRequest(MerchantSignupRequest request);

    MerchantResponse toResponse(Merchant merchant);
}
