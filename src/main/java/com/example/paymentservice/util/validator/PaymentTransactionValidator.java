package com.example.paymentservice.util.validator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import com.example.paymentservice.controller.dto.kafka.CancelPaymentRequest;
import com.example.paymentservice.controller.dto.kafka.CreatePaymentTransactionRequest;
import com.example.paymentservice.error.exception.PaymentTransactionValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentTransactionValidator {
    private final Validator validator;

    public void validateCreateTransactionRequest(CreatePaymentTransactionRequest transaction) {
        List<String> errors = new ArrayList<>();

        var violations = validator.validate(transaction, CreatePaymentTransactionRequest.class);
        var violationMessages = violations.stream().map(ConstraintViolation::getMessage).toList();
        errors.addAll(violationMessages);

        if(!errors.isEmpty()) {
            throw new PaymentTransactionValidationException(errors);
        }
    }

    public void validateCancelTransactionRequest(CancelPaymentRequest cancelPaymentRequest){
        List<String> errors = new ArrayList<>();

        var violations = validator.validate(cancelPaymentRequest, CancelPaymentRequest.class);
        var violationMessages = violations.stream().map(ConstraintViolation::getMessage).toList();
        errors.addAll(violationMessages);

        // Если есть ошибки, выбрасываем исключение
        if (!errors.isEmpty()) {
            throw new PaymentTransactionValidationException(errors);
        }
    }

}
