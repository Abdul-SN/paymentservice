package com.example.paymentservice.controller.dto;

import com.example.paymentservice.model.enums.RefundStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
public class RefundDto {
    private Long id;
    private Long transactionId;
    private BigDecimal refundedAmount;
    private RefundStatus status;
    private String reason;
    private OffsetDateTime executedAt;
}
