package com.example.finance.configuration;

import com.example.finance.factory.*;
import com.example.finance.model.dto.BudgetDto;
import com.example.finance.model.entity.BudgetEntity;
import com.example.finance.model.entity.TransactionsEntity;
import com.example.finance.repository.*;
import com.example.finance.utils.TestConstants;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestConfiguration
public class TestMockConfiguration {

    @Bean
    @Primary
    public BudgetRepository mockBudgetRepository() {
        BudgetRepository budgetRepository = Mockito.mock(BudgetRepository.class);
        BudgetEntity budgetEntity = BudgetMockFactory.createBudgetEntity();
        BudgetEntity budgetEntityWithoutUUID = BudgetMockFactory.createBudgetEntityWithoutUUID();
        BudgetEntity budgetEntityUpdated = BudgetMockFactory.createBudgetEntity(TestConstants.BUDGET_TEST_MONTH);

        Mockito.when(budgetRepository.findById(TestConstants.BUDGET_UUID))
                .thenReturn(Optional.of(budgetEntity));
        Mockito.when(budgetRepository.findById(TestConstants.USER_UUID))
                .thenReturn(Optional.of(budgetEntity));
        Mockito.when(budgetRepository.save(budgetEntityWithoutUUID))
                .thenReturn(budgetEntity);
        when(budgetRepository.saveAndFlush(any(BudgetEntity.class)))
                .thenReturn(budgetEntityUpdated);
        return budgetRepository;
    }

    @Bean
    @Primary
    public CategoriesRepository mockCategoriesRepository() {
        CategoriesRepository categoriesRepository = Mockito.mock(CategoriesRepository.class);
        Mockito.when(categoriesRepository.findById(TestConstants.CATEGORY_UUID))
                .thenReturn(Optional.of(CategoryMockFactory.createCategoryEntity()));
        return categoriesRepository;
    }

    @Bean
    @Primary
    public ReportRepository mockReportRepository() {
        ReportRepository reportRepository = Mockito.mock(ReportRepository.class);
        Mockito.when(reportRepository.findById(TestConstants.REPORT_UUID))
                .thenReturn(Optional.of(ReportMockFactory.createReportEntity()));
        return reportRepository;
    }

    @Bean
    @Primary
    public TransactionsRepository mockTransactionsRepository() {
        TransactionsRepository transactionsRepository = Mockito.mock(TransactionsRepository.class);
        TransactionsEntity transactionEntity = TransactionMockFactory.createTransactionEntity();
        TransactionsEntity transactionEntityWithoutId = TransactionMockFactory.createTransactionEntityWithoutId();
        TransactionsEntity transactionEntityForUpdate = TransactionMockFactory.createTransactionEntity(TestConstants.TRANSACTION_TEST_DESCRIPTION);

        Mockito.when(transactionsRepository.findById(TestConstants.TRANSACTION_UUID))
                .thenReturn(Optional.of(transactionEntity));
        Mockito.when(transactionsRepository.save(transactionEntityWithoutId))
                .thenReturn(transactionEntity);
        when(transactionsRepository.saveAndFlush(any(TransactionsEntity.class)))
                .thenReturn(transactionEntityForUpdate);
        return transactionsRepository;
    }

    @Bean
    @Primary
    public UserAccountRepository mockUserAccountRepository() {
        UserAccountRepository userAccountRepository = Mockito.mock(UserAccountRepository.class);
        Mockito.when(userAccountRepository.findById(TestConstants.USER_UUID))
                .thenReturn(Optional.of(UserMockFactory.createUserEntity()));
        return userAccountRepository;
    }
}
