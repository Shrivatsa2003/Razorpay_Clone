package com.shrivatsa.Razorpay.vault.repository;

import com.shrivatsa.Razorpay.vault.entity.VaultCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VaultCardRepository extends JpaRepository<VaultCard, UUID> {
}
