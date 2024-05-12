package com.example.finance.business;

import com.example.finance.exception.model.BackendException;
import com.example.finance.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.finance.business.ApiEndpoints.Endpoints.*;

@AllArgsConstructor
@Getter
public enum ApiEndpoints {

    USER_LOGIN(API + USER + LOGIN, false, null),
    USER_GET_ALL(API + USER + GET_ALL, false, null),
    USER_GET_BY_ID(API + USER + GET_BY_ID, false, null),
    USER_GET_CATEGORIES(API + USER + GET_CATEGORIES_BY_USER_ID, false, null),
    USER_GET_TRANSACTIONS(API + USER + GET_TRANSACTIONS_BY_USER_ID, false, null),
    USER_GET_BUDGETS(API + USER + GET_BUDGETS_BY_USER_ID, false, null),
    USER_GET_REPORT(API + USER + GET_REPORTS_BY_USER_ID, false, null),
    USER_CREATE(API + USER + CREATE, false, null),
    USER_UPDATE(API + USER + ID, false, null),
    USER_DELETE(API + USER + ID, false, null),

    CATEGORIES_GET_ALL(API + CATEGORIES + GET_ALL, false, null),
    CATEGORIES_GET_BY_ID(API + CATEGORIES + ID, false, null),
    CATEGORIES_CREATE(API + CATEGORIES + CREATE, false, null),
    CATEGORIES_UPDATE(API + CATEGORIES + ID, false, null),
    CATEGORIES_DELETE(API + CATEGORIES + ID, false, null),

    TRANSACTIONS_GET_BY_ID(API + TRANSACTION + ID, false, null),
    TRANSACTIONS_CREATE(API + TRANSACTION + CREATE, false, null),
    TRANSACTIONS_UPDATE(API + TRANSACTION + ID, false, null),
    TRANSACTIONS_DELETE(API + TRANSACTION + ID, false, null),

    BUDGETS_GET_BY_ID(API + BUDGET + ID, false, null),
    BUDGETS_CREATE(API + BUDGET + CREATE, false, null),
    BUDGETS_UPDATE(API + BUDGET + ID, false, null),
    BUDGETS_DELETE(API + BUDGET + ID, false, null),

    REPORT_GET_BY_ID(API + REPORT + ID, false, null),
    REPORT_CREATE(API + REPORT + CREATE, false, null),
    REPORT_UPDATE(API + REPORT + ID, false, null),
    REPORT_DELETE(API + REPORT + ID, false, null),

    ERROR_API("/api/error", false, null);

    private final String path;
    private final boolean authorization;
    private final UserRole role;

    private static final List<ApiEndpoints> VALUES = Arrays.stream(values()).toList();

    public static List<ApiEndpoints> getValues() {
        return VALUES;
    }

    public static ApiEndpoints from(String url) throws BackendException {
        return getValues().stream()
                .filter(apiEndpoints -> matchUrl(apiEndpoints.path, url))
                .findFirst()
                .orElseThrow(() -> new BackendException("No endpoint found"));
    }

    private static boolean matchUrl(String endpointPattern, String url) {
        String regex = endpointPattern.replace("{id}", "[\\w\\-]+");
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(url);
        return matcher.matches();
    }

    @UtilityClass
    public static final class Endpoints {
        public static final String API = "/api";
        public static final String USER = "/user";
        public static final String BUDGET = "/budget";
        public static final String CATEGORIES = "/categories";
        public static final String TRANSACTION = "/transactions";
        public static final String REPORT = "/reports";

        public static final String LOGIN = "/login";
        public static final String GET_BY_ID = "/get-by-id";
        public static final String GET_ALL = "/get-all";
        public static final String CREATE = "/create";
        public static final String ID = "/{id}";
        public static final String GET_CATEGORIES_BY_USER_ID = "/{id}/categories";
        public static final String GET_TRANSACTIONS_BY_USER_ID = "/{id}/transactions";
        public static final String GET_BUDGETS_BY_USER_ID = "/{id}/budgets";
        public static final String GET_REPORTS_BY_USER_ID = "/{id}/reports";



    }
}
