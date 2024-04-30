package com.example.finance.model.dto;

import java.util.Set;
import java.util.UUID;


public record UserAccountDto(UUID userId, String login, Set<RoleDto> roles) {
}
