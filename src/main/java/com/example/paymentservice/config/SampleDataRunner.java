package com.example.paymentservice.config;

import com.example.paymentservice.service.BankAccountService;
import com.example.paymentservice.service.PaymentTransactionService;
import com.example.paymentservice.model.enums.CurrencyType;
import com.example.paymentservice.controller.dto.kafka.CreatePaymentTransactionRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.BankAccountCreateRequest;
import org.openapitools.model.BankAccountResponse;
import org.openapitools.model.CurrencyAccount;
import org.openapitools.model.CurrencyAccountCreate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@Component
@RequiredArgsConstructor
public class SampleDataRunner implements CommandLineRunner {

    private final BankAccountService bankAccountService;
    private final PaymentTransactionService paymentTransactionService;

    @Value("${sampledata.enabled:false}")
    private boolean enabled;

    @Value("${sampledata.accounts:3}")
    private int accounts;

    @Value("${sampledata.transactions:10}")
    private int transactions;

    @Override
    public void run(String... args) {
        if (!enabled) {
            return;
        }

        List<Long> currencyIds = new ArrayList<>();
        for (int i = 1; i <= accounts; i++) {
            BankAccountCreateRequest req = new BankAccountCreateRequest();
            req.setCustomerId((long) i);
            req.setNumber("ACCT-" + i);

            CurrencyAccountCreate ca = new CurrencyAccountCreate();
            ca.setCurrency(CurrencyType.USD.name());
            ca.setBalance(BigDecimal.valueOf(1000));
            req.setCurrencyAccounts(List.of(ca));

            BankAccountResponse response = bankAccountService.create(req);
            for (CurrencyAccount account : response.getCurrencyAccounts()) {
                currencyIds.add(account.getCurrencyAccountId());
            }
        }

        Random random = new Random();
        for (int i = 0; i < transactions; i++) {
            Long source = currencyIds.get(random.nextInt(currencyIds.size()));
            Long dest = currencyIds.get(random.nextInt(currencyIds.size()));
            if (source.equals(dest)) {
                dest = currencyIds.get((random.nextInt(currencyIds.size())));
            }
            CreatePaymentTransactionRequest tx = new CreatePaymentTransactionRequest();
            tx.setSourceId(source);
            tx.setDestId(dest);
            tx.setAmount(BigDecimal.valueOf(random.nextInt(50) + 1));
            tx.setDescription("sample-" + i);
            paymentTransactionService.transfer(tx);
        }

        log.info("Sample data generated: {} accounts, {} transactions", accounts, transactions);
    }
}
