package com.example.finance.model.dto;

import com.example.finance.model.entity.BudgetEntity;
import com.example.finance.model.entity.TransactionsEntity;
import com.example.finance.model.enums.TransactionType;

import java.util.List;
import java.util.UUID;

public record CategoryDto(UUID categoryId,
                          UUID userId,
                          String name,
                          TransactionType transactionType,
                          String description) {
}
