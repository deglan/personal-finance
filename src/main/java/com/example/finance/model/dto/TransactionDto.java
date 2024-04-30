package com.example.finance.model.dto;

import com.example.finance.model.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record TransactionDto(UUID transactionID, UserAccountDto userAccountDto, CategoryDto categoryDto,
                             BigDecimal amount, TransactionType transactionType, LocalDate date, String description) {
}
