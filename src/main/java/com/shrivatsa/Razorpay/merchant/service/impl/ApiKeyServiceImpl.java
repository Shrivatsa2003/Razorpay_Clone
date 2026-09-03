package com.shrivatsa.Razorpay.merchant.service.impl;

import com.shrivatsa.Razorpay.common.exception.ResourceNotFoundException;
import com.shrivatsa.Razorpay.common.util.RandomizerUtil;
import com.shrivatsa.Razorpay.merchant.dto.Request.CreateApiKeyRequest;
import com.shrivatsa.Razorpay.merchant.dto.Response.ApiKeyCreateResponse;
import com.shrivatsa.Razorpay.merchant.dto.Response.ApiKeyResponse;
import com.shrivatsa.Razorpay.merchant.entity.ApiKey;
import com.shrivatsa.Razorpay.merchant.entity.Merchant;
import com.shrivatsa.Razorpay.merchant.mapper.ApiKeyMapper;
import com.shrivatsa.Razorpay.merchant.repository.ApiKeyRepository;
import com.shrivatsa.Razorpay.merchant.repository.MerchantRepository;
import com.shrivatsa.Razorpay.merchant.service.ApiKeyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApiKeyServiceImpl implements ApiKeyService {
    private final MerchantRepository merchantRepository;
    private final ApiKeyRepository apiKeyRepository;
    private final ApiKeyMapper apikeyMapper;
    private BCryptPasswordEncoder BCRPYT = new BCryptPasswordEncoder();
    @Override
    public ApiKeyCreateResponse create(UUID merchantId,CreateApiKeyRequest request) {
       Merchant merchant = merchantRepository.findById(merchantId)
               .orElseThrow(()-> new ResourceNotFoundException("merchant",merchantId));

       String keyId = "rzp_"+request.environment().name().toLowerCase()+"_"+ RandomizerUtil.randomBase64(24);
       String rawSecret =RandomizerUtil.randomBase64(40);

        ApiKey apiKey = ApiKey.builder()
                .merchant(merchant)
                .keyId(keyId)
                .keySecretHash(BCRPYT.encode(rawSecret))
                .environment(request.environment())
                .build();

        apiKey = apiKeyRepository.save(apiKey);

        return new ApiKeyCreateResponse(apiKey.getId(),keyId,rawSecret,request.environment());

    }

    @Override
    public List<ApiKeyResponse> listByMerchant(UUID merchantId) {
        return apikeyMapper.toResponseList(apiKeyRepository.findByMerchant_Id(merchantId));
    }

    @Override
    @Transactional
    public void revoke(UUID merchantId, UUID keyId) {
        ApiKey key = apiKeyRepository.findById(keyId)
                .filter(k -> k.getMerchant().getId().equals(merchantId))
                .orElseThrow(() -> new ResourceNotFoundException("ApiKey", keyId));

        key.setEnabled(false);
    }

    @Override
    @Transactional
    public @Nullable ApiKeyCreateResponse rotate(UUID merchantId, UUID keyId) {
        ApiKey apiKey = apiKeyRepository.findById(keyId)
                .filter(k -> k.getMerchant().getId().equals(merchantId))
                .orElseThrow(() -> new ResourceNotFoundException("ApiKey", keyId));
        if(!apiKey.isEnabled()){throw new RuntimeException("cannot rotate disable key");}
        String newRawSecret = RandomizerUtil.randomBase64(40);
        apiKey.setPreviousKeySecretHash(apiKey.getKeySecretHash());
        apiKey.setKeySecretHash(BCRPYT.encode(newRawSecret));
        apiKey.setRotatedAt(LocalDateTime.now());
        apiKey.setGracePeriodExpiresAt(LocalDateTime.now().plusHours(24));
        apiKey = apiKeyRepository.save(apiKey);

        return new ApiKeyCreateResponse(apiKey.getId(), apiKey.getKeyId(),
                newRawSecret, apiKey.getEnvironment());
    }
}
