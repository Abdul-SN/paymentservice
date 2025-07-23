package com.example.paymentservice.model.entity;

import jakarta.persistence.*;
import com.example.paymentservice.model.entity.account.CurrencyAccount;
import com.example.paymentservice.model.enums.PaymentTransactionStatus;
import com.example.paymentservice.model.enums.converter.PaymentTransactionStatusConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "payment_transaction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTransaction extends BaseEntity {

    private BigDecimal amountDebited;

    private BigDecimal amountCredited;

    private BigDecimal exchangeRate;

    @Convert(converter = PaymentTransactionStatusConverter.class)
    private PaymentTransactionStatus status;

    private String errorMessage;

    @ManyToOne
    @JoinColumn(name = "sourceCurrencyAccountId", nullable = false)
    private CurrencyAccount source;

    @ManyToOne
    @JoinColumn(name = "destinationCurrencyAccountId")
    private CurrencyAccount destination;

    @OneToMany(mappedBy = "paymentTransaction", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Refund> refunds;
}
