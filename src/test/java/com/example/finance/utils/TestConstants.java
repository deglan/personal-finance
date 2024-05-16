package com.example.finance.utils;

import com.example.finance.model.enums.TransactionType;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@UtilityClass
public class TestConstants {

    public UUID USER_UUID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
    public String USER_LOGIN = "user1";
    public String USER_PASSWORD = "password1";
    public String USER_ENCODED_PASSWORD = "password2";
    public String USER_EMAIL = "testUser@example.com";
    public final UUID CATEGORY_UUID = UUID.fromString("123e4567-e89b-12d3-a456-426614174001");
    public final String CATEGORY_NAME = "Groceries";
    public final String CATEGORY_DESCRIPTION = "Weekly food and supplies";

    public final UUID BUDGET_UUID = UUID.fromString("123e4567-e89b-12d3-a456-426614174002");
    public final BigDecimal BUDGET_AMOUNT = BigDecimal.valueOf(1000.00);
    public final int BUDGET_MONTH = 9;
    public final int BUDGET_YEAR = 2023;

    public final UUID TRANSACTION_UUID = UUID.fromString("123e4567-e89b-12d3-a456-426614174003");
    public final BigDecimal TRANSACTION_AMOUNT = BigDecimal.valueOf(100.00);
    public final TransactionType TRANSACTION_TYPE = TransactionType.EXPENSE;
    public final LocalDate TRANSACTION_DATE = LocalDate.now();
    public final String TRANSACTION_DESCRIPTION = "Sample transaction";

    public final UUID REPORT_UUID = UUID.fromString("123e4567-e89b-12d3-a456-426614174004");
    public final String REPORT_TYPE = "Monthly Expense Report";
    public final LocalDate REPORT_START_DATE = LocalDate.of(2023, 1, 1);
    public final LocalDate REPORT_END_DATE = LocalDate.of(2023, 1, 31);
    public final LocalDate REPORT_GENERATED_DATE = LocalDate.now();

    public final UUID TRANSFER_FROM_BUDGET_ID = UUID.fromString("123e4567-e89b-12d3-a456-426614174005");
    public final UUID TRANSFER_TO_BUDGET_ID = UUID.fromString("123e4567-e89b-12d3-a456-426614174006");
    public final BigDecimal TRANSFER_AMOUNT = BigDecimal.valueOf(100.00);
    public final String FROM_CATEGORY_NAME = "Groceries";
    public final String TO_CATEGORY_NAME = "Entertainment";
}
