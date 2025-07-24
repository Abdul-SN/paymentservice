package com.example.paymentservice.mapper;

import com.example.paymentservice.controller.dto.RefundDto;
import com.example.paymentservice.controller.dto.kafka.CancelPaymentRequest;
import com.example.paymentservice.model.entity.Refund;
import com.example.paymentservice.model.enums.RefundStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring")
public interface RefundMapper {

    Refund toEntity(CancelPaymentRequest cancelPaymentRequest,
                    RefundStatus status);


    @Mapping(source = "paymentTransaction.id", target = "transactionId")
    @Mapping(source = "createdAt", target = "executedAt", qualifiedByName = "mapLocalDateTimeToOffsetDateTime")
    RefundDto toDto(Refund refund);


    @Named("mapLocalDateTimeToOffsetDateTime")
    default OffsetDateTime mapLocalDateTimeToOffsetDateTime(LocalDateTime localDateTime) {
        return (localDateTime == null) ? null : localDateTime.atOffset(ZoneOffset.UTC);
    }
}
