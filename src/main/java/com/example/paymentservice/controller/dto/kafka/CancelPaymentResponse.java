package com.example.paymentservice.controller.dto.kafka;

import com.example.paymentservice.controller.dto.enums.CommandResultStatus;
import lombok.Data;

@Data
public class CancelPaymentResponse {
    private CommandResultStatus status;
    private String errorMessage;
}
