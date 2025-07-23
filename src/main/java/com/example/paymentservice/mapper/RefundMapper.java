package com.example.paymentservice.mapper;

import com.example.paymentservice.controller.dto.kafka.CancelPaymentRequest;
import com.example.paymentservice.controller.dto.kafka.CancelPaymentResponse;
import com.example.paymentservice.controller.dto.enums.CommandResultStatus;
import com.example.paymentservice.model.entity.Refund;
import com.example.paymentservice.model.enums.RefundStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface RefundMapper {

    Refund toEntity(CancelPaymentRequest cancelPaymentRequest,
                    RefundStatus status);

    @Mapping(source = "status", target = "status", qualifiedByName = "mapRefundStatusToCommandStatus")
    CancelPaymentResponse toResponse(Refund refund);

    @Named("mapRefundStatusToCommandStatus")
    default CommandResultStatus mapRefundStatusToCommandStatus(RefundStatus refundStatus) {
        if (refundStatus == null) {
            return CommandResultStatus.FAILED;
        }
        return switch (refundStatus) {
            case COMPLETED -> CommandResultStatus.SUCCESS;
            case FAILED -> CommandResultStatus.FAILED;
        };
    }
}
