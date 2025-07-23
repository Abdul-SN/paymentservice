package com.example.paymentservice.service.handler;

import com.example.paymentservice.controller.dto.kafka.CreatePaymentTransactionRequest;
import com.example.paymentservice.controller.kafka.producer.PaymentTransactionProducer;
import com.example.paymentservice.model.enums.PaymentTransactionCommand;
import com.example.paymentservice.service.PaymentTransactionService;
import com.example.paymentservice.util.validator.PaymentTransactionValidator;
import com.example.paymentservice.util.JsonConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Обрабатывает команды на возврат платежа
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CreatePaymentTransactionHandler implements PaymentTransactionCommandHandler {
    private final PaymentTransactionService paymentTransactionService;

    private final JsonConverter jsonConverter;
    private final PaymentTransactionValidator paymentTransactionValidator;
    private final PaymentTransactionProducer paymentTransactionProducer;

    /**
     * Переводит {@code amount} со счёта source на счет dest,
     * с конвертацией по курсу, если currency отличаются.
     */
    @Override
    public void processCommand(Long requestId, String message) {
        var request = jsonConverter.fromJson(message, CreatePaymentTransactionRequest.class);
        paymentTransactionValidator.validateCreateTransactionRequest(request);

        var tx = paymentTransactionService.transfer(request);

        paymentTransactionProducer.sendCommandResult(
                requestId,
                PaymentTransactionCommand.CREATE,
                tx.toString()
        );
    }
}
