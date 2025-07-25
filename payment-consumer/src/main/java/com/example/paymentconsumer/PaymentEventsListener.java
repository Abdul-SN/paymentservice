package com.example.paymentconsumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaymentEventsListener {

    @KafkaListener(topics = {"dbserver1.public.payment_transaction", "dbserver1.public.refund"})
    public void handle(String message) {
        log.info("Received event: {}", message);
    }
}
