package com.example.paymentservice.model.entity;

import jakarta.persistence.*;
import com.example.paymentservice.model.enums.RefundStatus;
import com.example.paymentservice.model.enums.converter.RefundStatusConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "refund")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Refund extends BaseEntity {

    private BigDecimal refundedAmount;

    @Convert(converter = RefundStatusConverter.class)
    private RefundStatus status;

    private String reason;

    @ManyToOne
    @JoinColumn(name = "paymentTransactionId", referencedColumnName = "id", nullable = false)
    private PaymentTransaction paymentTransaction;
}
