package com.example.paymentservice.controller.rest;

import com.example.paymentservice.controller.dto.RefundDto;
import com.example.paymentservice.controller.dto.TransactionDto;
import com.example.paymentservice.controller.dto.kafka.CancelPaymentRequest;
import com.example.paymentservice.controller.dto.kafka.CreatePaymentTransactionRequest;
import com.example.paymentservice.mapper.PaymentTransactionMapper;
import com.example.paymentservice.mapper.RefundMapper;
import com.example.paymentservice.service.PaymentTransactionService;
import com.example.paymentservice.service.refund.RefundService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class PaymentTransactionController {
    private final PaymentTransactionService paymentTransactionService;
    private final RefundService refundService;
    private final PaymentTransactionMapper paymentTransactionMapper;
    private final RefundMapper refundMapper;

    @PostMapping
    public ResponseEntity<TransactionDto> createTransaction(@RequestBody CreatePaymentTransactionRequest request) {
        var transaction = paymentTransactionService.transfer(request);
        var dto = paymentTransactionMapper.toDto(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDto> getTransaction(@PathVariable Long id) {
        var transaction = paymentTransactionService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found: " + id));
        var dto = paymentTransactionMapper.toDto(transaction);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/{id}/refund")
    public ResponseEntity<RefundDto> createRefund(@PathVariable Long id,
                                                  @RequestBody CancelPaymentRequest request) {
        request.setTransactionId(id);
        var refund = refundService.createRefund(request);
        var dto = refundMapper.toDto(refund);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("/{id}/refunds")
    public List<RefundDto> getRefunds(@PathVariable Long id) {
        return refundService.getRefundsForTransaction(id).stream()
                .map(refundMapper::toDto)
                .collect(Collectors.toList());
    }
}
