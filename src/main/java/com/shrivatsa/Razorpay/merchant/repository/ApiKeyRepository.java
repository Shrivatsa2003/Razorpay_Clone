package com.shrivatsa.Razorpay.merchant.repository;

import com.shrivatsa.Razorpay.merchant.entity.ApiKey;
import org.hibernate.query.SelectionQuery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ApiKeyRepository extends JpaRepository<ApiKey, UUID> {
    List<ApiKey> findByMerchant_Id(UUID merchantId);
}
