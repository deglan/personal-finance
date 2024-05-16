package com.example.finance.factory;

import com.example.finance.model.dto.TransactionDto;
import com.example.finance.model.entity.CategoryEntity;
import com.example.finance.model.entity.TransactionsEntity;
import com.example.finance.model.entity.UserAccountEntity;
import com.example.finance.utils.TestConstants;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TransactionMockFactory {

    public TransactionsEntity createTransactionEntity(UserAccountEntity user, CategoryEntity category) {
        return TransactionsEntity.builder()
                .transactionID(TestConstants.TRANSACTION_UUID)
                .userAccountEntity(user)
                .categoryEntity(category)
                .amount(TestConstants.TRANSACTION_AMOUNT)
                .transactionType(TestConstants.TRANSACTION_TYPE)
                .date(TestConstants.TRANSACTION_DATE)
                .description(TestConstants.TRANSACTION_DESCRIPTION)
                .build();
    }

    public TransactionDto createTransactionDto() {
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
}
