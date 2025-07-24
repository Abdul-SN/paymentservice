package com.example.paymentservice.error.exception;

public class CurrencyUnavailableException extends RuntimeException {
    public CurrencyUnavailableException(String message) {
        super(message);
    }
}
