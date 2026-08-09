package com.shrivatsa.Razorpay.Payment.mapper;

import com.shrivatsa.Razorpay.Payment.dto.response.OrderResponse;
import com.shrivatsa.Razorpay.Payment.entity.OrderRecord;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {

    OrderResponse toResponse(OrderRecord orderRecord);
}
