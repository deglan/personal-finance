package com.example.finance.auth;

import com.example.finance.model.enums.UserRole;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class RoleConverter implements AttributeConverter<List<UserRole>, String> {

    private static final String SEPARATOR = "::";

    @Override
    public String convertToDatabaseColumn(List<UserRole> attributes) {
        if (attributes == null) {
            return null;
        }
        return attributes.stream()
                .map(Enum::toString)
                .collect(Collectors.joining(SEPARATOR));
    }

    @Override
    public List<UserRole> convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return Arrays.stream(dbData.split(SEPARATOR))
                .map(UserRole::from)
                .toList();
    }
}
