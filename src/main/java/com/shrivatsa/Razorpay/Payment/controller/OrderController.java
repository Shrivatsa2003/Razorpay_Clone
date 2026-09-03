package com.shrivatsa.Razorpay.Payment.controller;

import com.shrivatsa.Razorpay.Payment.dto.request.CreateOrderRequest;
import com.shrivatsa.Razorpay.Payment.dto.response.OrderResponse;
import com.shrivatsa.Razorpay.Payment.service.OrderService;
import com.shrivatsa.Razorpay.merchant.securtiy.MerchantContext;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MerchantContext merchantContext;
    @PostMapping
    public ResponseEntity<OrderResponse> create(@RequestBody CreateOrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.create(merchantContext.getMerchantId(),request));
    }
}
