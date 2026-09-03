package com.shrivatsa.Razorpay.merchant.service;

import com.shrivatsa.Razorpay.merchant.dto.Request.LoginRequest;
import com.shrivatsa.Razorpay.merchant.dto.Request.MerchantSignupRequest;
import com.shrivatsa.Razorpay.merchant.dto.Response.LoginResponse;
import com.shrivatsa.Razorpay.merchant.dto.Response.MerchantResponse;
import jakarta.validation.Valid;

public interface AuthService {
    MerchantResponse signup( MerchantSignupRequest request);

    LoginResponse login(LoginRequest request);
}
