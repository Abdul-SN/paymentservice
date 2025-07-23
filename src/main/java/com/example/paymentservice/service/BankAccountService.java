package com.example.paymentservice.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import com.example.paymentservice.mapper.BankAccountMapper;
import com.example.paymentservice.model.entity.account.BankAccount;
import com.example.paymentservice.repository.BankAccountRepository;
import com.example.paymentservice.service.currency.CurrencyAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.BankAccountCreateRequest;
import org.openapitools.model.BankAccountResponse;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Slf4j
@Service
@RequiredArgsConstructor
public class BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final BankAccountMapper bankAccountMapper;
    private final CurrencyAccountService currencyAccountService;

    @Transactional
    public BankAccountResponse findByCustomerId(@NotNull Long customerId) throws EntityNotFoundException {
        var entity = bankAccountRepository.findByCustomerId(customerId).orElseThrow(
                () -> new EntityNotFoundException("Bank account with customer id " + id + " not found")
        );
        return bankAccountMapper.toDto(entity);
    }

    @Transactional
    public Optional<BankAccount> findById(@NotNull Long id) {
        return bankAccountRepository.findById(id);
    }

    @Transactional
    public BankAccountResponse create(BankAccountCreateRequest request) {
        var optionalAccount = bankAccountRepository.findByCustomerId(request.getCustomerId());

        if (optionalAccount.isPresent()) {
            log.info("Bank account with customer id {} already exists", optionalAccount.get().getCustomerId());
            return bankAccountMapper.toDto(optionalAccount.get());
        }
        var temp = bankAccountMapper.toEntity(request);
        var bankAccount = bankAccountRepository.save(temp);
        var currencyAccounts = currencyAccountService.saveAll(request.getCurrencyAccounts(), bankAccount);
        bankAccount.setCurrencyAccounts(currencyAccounts);

        return bankAccountMapper.toDto(bankAccount);
    }
}
