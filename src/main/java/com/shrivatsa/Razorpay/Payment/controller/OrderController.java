package com.shrivatsa.Razorpay.Payment.controller;

import com.shrivatsa.Razorpay.Payment.dto.request.CreateOrderRequest;
import com.shrivatsa.Razorpay.Payment.dto.response.OrderResponse;
import com.shrivatsa.Razorpay.Payment.service.OrderService;
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
    UUID merchantId = UUID.fromString("1f6caca9-cab9-40cd-a491-a714146e5a55"); //TODO: replace it with MerchantContext

    @PostMapping
    public ResponseEntity<OrderResponse> create(@RequestBody CreateOrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.create(merchantId,request));
    }
}
