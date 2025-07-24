package com.example.paymentservice.service.refund;

import com.example.paymentservice.controller.dto.kafka.CancelPaymentRequest;
import com.example.paymentservice.mapper.RefundMapper;
import com.example.paymentservice.model.entity.Refund;
import com.example.paymentservice.model.enums.RefundStatus;
import com.example.paymentservice.repository.RefundRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefundService {
    private final RefundRepository refundRepository;
    private final RefundCalculator refundCalculator;
    private final RefundPolicy refundPolicy;
    private final RefundMapper refundMapper;


    @Transactional
    public Refund createRefund(CancelPaymentRequest request) {
        var transaction = refundPolicy.checkAndFetchTransaction(request.getTransactionId());
        var remainingAmount = refundPolicy.calculateRemainingRefundable(transaction, request.getRefundedAmount());

        refundCalculator.applyRefund(transaction, remainingAmount);
        var refund = refundMapper.toEntity(request, RefundStatus.COMPLETED);
        refund.setPaymentTransaction(transaction);
        return refundRepository.save(refund);
    }

    @Transactional(readOnly = true)
    public java.util.List<Refund> getRefundsForTransaction(Long transactionId) {
        return refundRepository.findAllByPaymentTransactionId(transactionId);
    }
}
