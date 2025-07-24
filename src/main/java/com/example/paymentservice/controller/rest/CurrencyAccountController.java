package com.example.paymentservice.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import com.example.paymentservice.controller.dto.CurrencyAccountDto;
import com.example.paymentservice.service.currency.CurrencyAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.CurrencyAccountCreate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/currencyAccounts")
@RequiredArgsConstructor
public class CurrencyAccountController {
    private final CurrencyAccountService currencyAccountService;

    @Operation(summary = "Получить все валютные счета пользователя")
    @GetMapping
    public List<CurrencyAccountDto> getAccounts(
            @Parameter(description = "ID пользователя", required = true)
            @RequestParam Long userId
    ) {
        return currencyAccountService.getUserAccounts(userId);
    }

    @Operation(summary = "Открыть валютный субсчёт")
    @PostMapping
    public ResponseEntity<CurrencyAccountDto> createAccount(
            @RequestParam Long bankAccountId,
            @RequestBody CurrencyAccountCreate request
    ) {
        CurrencyAccountDto dto = currencyAccountService.createAccount(bankAccountId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
}
