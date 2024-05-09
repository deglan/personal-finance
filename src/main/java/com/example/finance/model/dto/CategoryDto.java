package com.example.finance.model.dto;

import com.example.finance.model.enums.TransactionType;

import java.util.UUID;

public record CategoryDto(UUID categoryId,
                          UUID userId,
                          String name,
                          TransactionType transactionType,
                          String description) {
}
