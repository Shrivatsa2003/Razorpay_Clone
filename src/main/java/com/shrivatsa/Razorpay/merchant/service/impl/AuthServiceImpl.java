package com.shrivatsa.Razorpay.merchant.service.impl;

import com.shrivatsa.Razorpay.common.enums.MerchantStatus;
import com.shrivatsa.Razorpay.common.enums.UserRole;
import com.shrivatsa.Razorpay.common.exception.DuplicateResourceException;
import com.shrivatsa.Razorpay.common.exception.ResourceNotFoundException;
import com.shrivatsa.Razorpay.merchant.dto.Request.LoginRequest;
import com.shrivatsa.Razorpay.merchant.dto.Request.MerchantSignupRequest;
import com.shrivatsa.Razorpay.merchant.dto.Response.LoginResponse;
import com.shrivatsa.Razorpay.merchant.dto.Response.MerchantResponse;
import com.shrivatsa.Razorpay.merchant.entity.AppUser;
import com.shrivatsa.Razorpay.merchant.entity.Merchant;
import com.shrivatsa.Razorpay.merchant.mapper.MerchantMapper;
import com.shrivatsa.Razorpay.merchant.repository.AppUserRepository;
import com.shrivatsa.Razorpay.merchant.repository.MerchantRepository;
import com.shrivatsa.Razorpay.merchant.securtiy.JwtUtil;
import com.shrivatsa.Razorpay.merchant.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AppUserRepository appUserRepository;
    private final MerchantRepository merchantRepository;
    private final MerchantMapper merchantMapper;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public MerchantResponse signup(MerchantSignupRequest request) {

        if(merchantRepository.existsByEmail(request.email())){
            throw new DuplicateResourceException("DUPLICATE_MERCHANT_EMAIL","Email already exists");
        }

        Merchant merchant =merchantMapper.toEntityFromSignUpRequest(request);
        merchant.setStatus(MerchantStatus.PENDING_KYC);
        merchant =  merchantRepository.save(merchant);
        AppUser appUser = AppUser.builder()
                .email(request.email())
                .merchant(merchant)
                .passwordHash(passwordEncoder.encode(request.password()))
                .role(UserRole.OWNER)
                .build();
        appUserRepository.save(appUser);
        return new MerchantResponse(merchant.getId(),merchant.getName(), merchant.getEmail(),merchant.getBusinessName(),merchant.getBusinessType(),merchant.getStatus());
    }
    @Override
    public LoginResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        AppUser appUser = appUserRepository.findByEmail(request.email())
                .orElseThrow(() -> new ResourceNotFoundException("User", request.email()));

        String token = jwtUtil.generateAccessToken(request.email(), appUser.getMerchant().getId(), appUser.getRole().toString());

        return new LoginResponse(token);
    }
}
