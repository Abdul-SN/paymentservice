package com.example.paymentservice.model.entity.account;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import com.example.paymentservice.model.entity.BaseEntity;
import com.example.paymentservice.model.entity.PaymentTransaction;
import com.example.paymentservice.model.enums.CurrencyType;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "currency_account")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyAccount extends BaseEntity {
    @NotNull
    private CurrencyType currencyType;

    @PositiveOrZero
    private BigDecimal balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_account_id", nullable = false)
    private BankAccount bankAccount;

    @OneToMany(
            mappedBy = "source",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<PaymentTransaction> sourceTransactions = new ArrayList<>();

    @OneToMany(
            mappedBy = "destination",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<PaymentTransaction> transactions = new ArrayList<>();
}
