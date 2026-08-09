package com.shrivatsa.Razorpay.operations.entity;

import com.shrivatsa.Razorpay.common.entity.BaseEntity;
import jakarta.persistence.Embeddable;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SettlementPaymentId   extends BaseEntity {

    private UUID settlementId;

    private UUID paymentId;
}
