package com.example.finance.factory;

import com.example.finance.model.dto.TransactionDto;
import com.example.finance.model.entity.CategoryEntity;
import com.example.finance.model.entity.TransactionsEntity;
import com.example.finance.model.entity.UserAccountEntity;
import com.example.finance.utils.TestConstants;
import lombok.experimental.UtilityClass;

import java.util.Optional;
import java.util.UUID;

@UtilityClass
public class TransactionMockFactory {

    public TransactionsEntity createTransactionEntityWithUserAndCategory(UserAccountEntity user, CategoryEntity category) {
        return TransactionsEntity.builder()
                .transactionId(TestConstants.TRANSACTION_UUID)
                .userAccountEntity(user)
                .categoryEntity(category)
                .amount(TestConstants.TRANSACTION_AMOUNT)
                .transactionType(TestConstants.TRANSACTION_TYPE)
                .date(TestConstants.TRANSACTION_DATE)
                .description(TestConstants.TRANSACTION_DESCRIPTION)
                .build();
    }

    public TransactionsEntity createTransactionEntity() {
        return TransactionsEntity.builder()
                .transactionId(TestConstants.TRANSACTION_UUID)
                .userAccountEntity(UserMockFactory.createUserEntity())
                .categoryEntity(CategoryMockFactory.createCategoryEntity())
                .amount(TestConstants.TRANSACTION_AMOUNT)
                .transactionType(TestConstants.TRANSACTION_TYPE)
                .date(TestConstants.TRANSACTION_DATE)
                .description(TestConstants.TRANSACTION_DESCRIPTION)
                .build();
    }

    public TransactionsEntity createTransactionEntityWithoutId() {
        return TransactionsEntity.builder()
                .transactionId(null)
                .userAccountEntity(UserMockFactory.createUserEntity())
                .categoryEntity(CategoryMockFactory.createCategoryEntity())
                .amount(TestConstants.TRANSACTION_AMOUNT)
                .transactionType(TestConstants.TRANSACTION_TYPE)
                .date(TestConstants.TRANSACTION_DATE)
                .description(TestConstants.TRANSACTION_DESCRIPTION)
                .build();
    }

    public TransactionsEntity createTransactionEntity(String description) {
        return TransactionsEntity.builder()
                .transactionId(TestConstants.TRANSACTION_UUID)
                .userAccountEntity(UserMockFactory.createUserEntity())
                .categoryEntity(CategoryMockFactory.createCategoryEntity())
                .amount(TestConstants.TRANSACTION_AMOUNT)
                .transactionType(TestConstants.TRANSACTION_TYPE)
                .date(TestConstants.TRANSACTION_DATE)
                .description(description)
                .build();
    }

    public TransactionDto createTransactionDtoFullData() {
        return new TransactionDto(
                TestConstants.TRANSACTION_UUID,
                TestConstants.USER_UUID,
                TestConstants.CATEGORY_UUID,
                TestConstants.TRANSACTION_AMOUNT,
                TestConstants.TRANSACTION_TYPE,
                TestConstants.TRANSACTION_DATE,
                TestConstants.TRANSACTION_DESCRIPTION
        );
    }

    public TransactionDto createTransactionDto(UUID uuid) {
        return new TransactionDto(
                uuid,
                TestConstants.USER_UUID,
                TestConstants.CATEGORY_UUID,
                TestConstants.TRANSACTION_AMOUNT,
                TestConstants.TRANSACTION_TYPE,
                TestConstants.TRANSACTION_DATE,
                TestConstants.TRANSACTION_DESCRIPTION
        );
    }

    public TransactionDto createTransactionDto(String description) {
        return new TransactionDto(
                TestConstants.TRANSACTION_UUID,
                TestConstants.USER_UUID,
                TestConstants.CATEGORY_UUID,
                TestConstants.TRANSACTION_AMOUNT,
                TestConstants.TRANSACTION_TYPE,
                TestConstants.TRANSACTION_DATE,
                description
        );
    }

    public TransactionDto createTransactionDtoWithoutTransactionUUID() {
        return createTransactionDto((UUID) null);
    }
}
