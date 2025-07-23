package com.example.paymentservice.mapper;

import com.example.paymentservice.model.entity.account.BankAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.openapitools.model.BankAccountCreateRequest;
import org.openapitools.model.BankAccountResponse;

@Mapper(componentModel = "spring")
public interface BankAccountMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "currencyAccounts", ignore = true)
    BankAccount toEntity(BankAccountCreateRequest request);

    BankAccountResponse toDto(BankAccount bankAccount);

}
