package com.example.finance.model.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.UUID;

public record BudgetDto(
        UUID budgetId,
        @NotNull
        UUID userId,
        @NotNull
        UUID categoryId,
        @DecimalMin("0.00")
        @DecimalMax("1000000.00")
        @Digits(integer = 10, fraction = 2)
        BigDecimal amount,
        @Min(1)
        @Max(12)
        Integer month,
        Integer year,
        int businessObjectVersion) {
}
