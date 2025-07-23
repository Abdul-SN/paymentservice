package com.example.paymentservice.config;

import com.example.paymentservice.model.enums.PaymentTransactionCommand;
import com.example.paymentservice.service.handler.CreatePaymentTransactionHandler;
import com.example.paymentservice.service.handler.PaymentTransactionCommandHandler;
import com.example.paymentservice.service.handler.RefundPaymentTransactionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class PaymentTransactionCommandConfig {

    @Bean
    public Map<PaymentTransactionCommand, PaymentTransactionCommandHandler> commandHandlers(
            CreatePaymentTransactionHandler createPaymentTransactionHandler,
            RefundPaymentTransactionHandler refundPaymentTransactionHandler
    ) {
        return Map.of(
                PaymentTransactionCommand.CREATE, createPaymentTransactionHandler,
                PaymentTransactionCommand.REFUND, refundPaymentTransactionHandler
        );
    }
}
