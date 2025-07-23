package com.example.paymentservice.model.enums.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import com.example.paymentservice.model.enums.PaymentTransactionStatus;

@Converter(autoApply = true)
public class PaymentTransactionStatusConverter implements AttributeConverter<PaymentTransactionStatus, String> {

    @Override
    public String convertToDatabaseColumn(PaymentTransactionStatus status) {
        return (status == null) ? null : status.name();
    }

    @Override
    public PaymentTransactionStatus convertToEntityAttribute(String dbData) {
        return (dbData == null) ? null : PaymentTransactionStatus.fromString(dbData);
    }
}