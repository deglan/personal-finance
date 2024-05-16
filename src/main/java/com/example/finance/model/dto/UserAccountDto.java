package com.example.finance.model.dto;

import com.example.finance.model.enums.UserRole;

import java.util.List;
import java.util.UUID;

public record UserAccountDto(UUID userId,
                             String login,
                             List<UserRole> role) {
}
