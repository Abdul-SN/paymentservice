package com.example.paymentservice.controller.dto;

import com.example.paymentservice.model.enums.PaymentTransactionStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
public class TransactionDto {
    private Long id;
    private Long sourceCurrencyAccountId;
    private Long destinationCurrencyAccountId;
    private BigDecimal amountDebited;
    private BigDecimal amountCredited;
    private BigDecimal exchangeRate;
    private PaymentTransactionStatus status;
    private String errorMessage;
    private OffsetDateTime executedAt;
}
