package com.example.paymentservice.service.handler;

public interface PaymentTransactionCommandHandler {
    void processCommand(Long requestId, String message);
}
