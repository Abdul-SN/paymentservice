package com.example.paymentservice.mapper;

import com.example.paymentservice.controller.dto.TransactionDto;
import com.example.paymentservice.model.entity.PaymentTransaction;
import com.example.paymentservice.model.entity.account.CurrencyAccount;
import com.example.paymentservice.model.enums.PaymentTransactionStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring")
public interface PaymentTransactionMapper {


    @Mapping(source = "createdAt", target = "executedAt", qualifiedByName = "mapLocalDateTimeToOffsetDateTime")
    @Mapping(source = "source.id", target = "sourceCurrencyAccountId")
    @Mapping(source = "destination.id", target = "destinationCurrencyAccountId")
    TransactionDto toDto(PaymentTransaction paymentTransaction);

    @Named("mapLocalDateTimeToOffsetDateTime")
    default OffsetDateTime mapLocalDateTimeToOffsetDateTime(LocalDateTime localDateTime) {
        return (localDateTime == null) ? null : localDateTime.atOffset(ZoneOffset.UTC);
    }


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "SUCCESS")
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)

    PaymentTransaction toEntity(
            CurrencyAccount source,
            CurrencyAccount destination,
            BigDecimal amountDebited,
            BigDecimal amountCredited,
            BigDecimal exchangeRate
    );
}
