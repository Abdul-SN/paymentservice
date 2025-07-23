package com.example.paymentservice.mapper;

import com.example.paymentservice.controller.dto.CurrencyAccountDto;
import com.example.paymentservice.model.entity.account.CurrencyAccount;
import com.example.paymentservice.model.enums.CurrencyType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.openapitools.model.CurrencyAccountCreate;

@Mapper(componentModel = "spring")
public interface CurrencyAccountMapper {
    CurrencyAccountDto toDto(CurrencyAccount currencyAccount);

    @Mapping(source = "currency", target = "currencyType", qualifiedByName = "toCurrencyType")
    CurrencyAccount toEntity(CurrencyAccountCreate create);

    @Named("toCurrencyType")
    default CurrencyType toCurrencyType(String currency) {
        if (currency == null || currency.isBlank()) {
            return null;
        }
        try {
            return CurrencyType.valueOf(currency);
        } catch (IllegalArgumentException iae) {
            // you might want to log or rethrow a custom exception here
            return null;
        }
    }
}
