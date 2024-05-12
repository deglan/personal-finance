package com.example.finance.auth;

import com.example.finance.model.enums.UserRole;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Converter
public class RoleConverter implements AttributeConverter<List<UserRole>, String> {

    private static final String SEPARATOR = "::";

    @Override
    public String convertToDatabaseColumn(List<UserRole> attributes) {
        return Optional.ofNullable(attributes)
                .map(att -> att.stream()
                        .map(Enum::toString)
                        .collect(Collectors.joining(SEPARATOR)))
                .orElse(null);
    }

    @Override
    public List<UserRole> convertToEntityAttribute(String dbData) {
        return Optional.ofNullable(dbData)
                .map(data -> Arrays.stream(dbData.split(SEPARATOR))
                        .map(UserRole::from)
                        .toList())
                .orElse(null);
    }
}
