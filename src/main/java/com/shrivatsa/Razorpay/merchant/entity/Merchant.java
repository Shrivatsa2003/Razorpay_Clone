package com.shrivatsa.Razorpay.merchant.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name="merchant")
public class Merchant {
    private UUID id;

}
