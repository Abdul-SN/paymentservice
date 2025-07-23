package com.example.paymentservice.controller.dto.kafka;

import com.example.paymentservice.controller.dto.enums.CommandResultStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentTransactionResponse {
    private CommandResultStatus status;
    private String errorMessage;
    private LocalDateTime executedAt;
}
