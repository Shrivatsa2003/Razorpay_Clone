package com.shrivatsa.Razorpay.merchant.mapper;

import com.shrivatsa.Razorpay.merchant.dto.Response.ApiKeyCreateResponse;
import com.shrivatsa.Razorpay.merchant.dto.Response.ApiKeyResponse;
import com.shrivatsa.Razorpay.merchant.entity.ApiKey;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ApiKeyMapper {

    ApiKeyCreateResponse toCreateResponse(ApiKey apiKey);

    List<ApiKeyResponse> toResponseList(List<ApiKey> apiKeyList);
}
