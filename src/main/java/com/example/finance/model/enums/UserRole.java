package com.example.finance.model.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum UserRole {

    ADMINISTRATOR, USER, VIEWER, EDITOR, AUDITOR;


    private static final Map<String, UserRole> BY_NAME = Arrays.stream(values())
            .collect(Collectors.toMap(Enum::toString, Function.identity()));

    public static UserRole from(String name) {
        return BY_NAME.get(name);
    }
}
