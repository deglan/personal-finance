package com.example.finance.business;

import com.example.finance.exception.model.FailureOperationException;
import com.example.finance.model.entity.RoleEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

import static com.example.finance.business.ApiEndpoints.Endpoints.*;

@AllArgsConstructor
@Getter
public enum ApiEndpoints {

    USER_LOGIN(API + USER + LOGIN, false, null);

    private final String path;
    private final boolean authorization;
    private final RoleEntity roleEntity;

    private static final List<ApiEndpoints> VALUES = Arrays.stream(values()).toList();

    public static List<ApiEndpoints> getValues() {
        return VALUES;
    }

    public static ApiEndpoints from(String url) throws FailureOperationException {
        return getValues().stream()
                .filter(apiEndpoints -> url.contains(apiEndpoints.path))
                .findFirst()
                .orElseThrow(() -> new FailureOperationException("No endpoint found"));
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Endpoints {
        public static final String API = "/api";
        public static final String USER = "/user";
        public static final String LOGIN = "/login";
    }
}
