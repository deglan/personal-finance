package com.example.finance.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferFunds(@NotNull
                            UUID userId,

                            @NotNull
                            String fromCategoryName,

                            @NotNull
                            String toCategoryName,

                            @NotNull
                            @Positive
                            BigDecimal amount,

                            @NotNull
                            UUID fromBudgetId,

                            @NotNull
                            UUID toBudgetId) {
}
