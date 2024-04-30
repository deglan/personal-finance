package com.example.finance.model.dto;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public record BudgetDto(UUID budgetId, UserAccountDto userAccountDto, Set<CategoryDto> categoryDtos, BigDecimal amount,
                        Integer month, Integer year) {
}
