package com.example.finance.service;

import com.example.finance.exception.model.BackendException;
import com.example.finance.factory.BudgetMockFactory;
import com.example.finance.factory.CategoryMockFactory;
import com.example.finance.factory.TransferFundsMockFactory;
import com.example.finance.factory.UserMockFactory;
import com.example.finance.mapper.CategoryMapper;
import com.example.finance.model.dto.TransferFunds;
import com.example.finance.model.entity.BudgetEntity;
import com.example.finance.model.entity.CategoryEntity;
import com.example.finance.model.entity.UserAccountEntity;
import com.example.finance.repository.BudgetRepository;
import com.example.finance.repository.CategoriesRepository;
import com.example.finance.repository.UserAccountRepository;
import com.example.finance.utils.MessageConstants;
import com.example.finance.utils.TestConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    CategoriesRepository categoriesRepository;
    @Mock
    UserAccountRepository userAccountRepository;
    @Mock
    BudgetRepository budgetRepository;
    @Mock
    CategoryMapper categoryMapper;
    CategoryService categoryService;

    @BeforeEach
    public void setUp() {
        categoryService = new CategoryService(categoriesRepository, userAccountRepository, budgetRepository, categoryMapper);
    }

    @Test
    void transferFunds_changeFundsBetweenCategories_success() {
        // GIVEN
        UserAccountEntity user = UserMockFactory.createUserEntity();
        CategoryEntity fromCategory = CategoryMockFactory.createCategoryEntity(user);
        CategoryEntity toCategory = CategoryMockFactory.createCategoryEntity(user);
        BudgetEntity fromBudget = BudgetMockFactory.createBudgetEntity(user, fromCategory);
        BudgetEntity toBudget = BudgetMockFactory.createBudgetEntity(user, toCategory);
        TransferFunds transferFunds = TransferFundsMockFactory.createTransferFundsDto();
        fromBudget.setAmount(BigDecimal.valueOf(500.00));
        toBudget.setAmount(BigDecimal.valueOf(300.00));

        // Mock repository responses
        when(categoriesRepository
                .findByUserAccountEntityUserIdAndName(transferFunds.userId(), transferFunds.fromCategoryName()))
                .thenReturn(Optional.of(fromCategory));
        when(categoriesRepository
                .findByUserAccountEntityUserIdAndName(transferFunds.userId(), transferFunds.toCategoryName()))
                .thenReturn(Optional.of(toCategory));
        when(budgetRepository
                .findByUserAccountEntityUserIdAndCategoryEntityCategoryIdAndBudgetId(transferFunds.userId(),
                        fromCategory.getCategoryId(), transferFunds.fromBudgetId()))
                .thenReturn(Optional.of(fromBudget));
        when(budgetRepository
                .findByUserAccountEntityUserIdAndCategoryEntityCategoryIdAndBudgetId(transferFunds.userId(),
                        toCategory.getCategoryId(), transferFunds.toBudgetId()))
                .thenReturn(Optional.of(toBudget));


        // WHEN
        categoryService.transferFundsBetweenCategories(transferFunds);

        // THEN
        assertEquals(BigDecimal.valueOf(400.00), fromBudget.getAmount());
        assertEquals(BigDecimal.valueOf(400.00), toBudget.getAmount());
    }

    @Test
    void transferFunds_changeFundsBetweenCategories_insufficientFunds() {
        //GIVEN
        UserAccountEntity user = UserMockFactory.createUserEntity();
        CategoryEntity fromCategory = CategoryMockFactory.createCategoryEntity(user);
        CategoryEntity toCategory = CategoryMockFactory.createCategoryEntity(user);
        BudgetEntity fromBudget = BudgetMockFactory.createBudgetEntity(user, fromCategory);
        BudgetEntity toBudget = BudgetMockFactory.createBudgetEntity(user, toCategory);
        TransferFunds transferFunds = TransferFundsMockFactory.createTransferFundsDto();

        fromBudget.setAmount(BigDecimal.valueOf(50.00));
        toBudget.setAmount(BigDecimal.valueOf(300.00));
        when(categoriesRepository
                .findByUserAccountEntityUserIdAndName(transferFunds.userId(),
                        TestConstants.FROM_CATEGORY_NAME))
                .thenReturn(Optional.of(fromCategory));
        when(categoriesRepository
                .findByUserAccountEntityUserIdAndName(transferFunds.userId(),
                        TestConstants.TO_CATEGORY_NAME))
                .thenReturn(Optional.of(toCategory));
        when(budgetRepository
                .findByUserAccountEntityUserIdAndCategoryEntityCategoryIdAndBudgetId(transferFunds.userId(),
                        fromCategory.getCategoryId(), TestConstants.TRANSFER_FROM_BUDGET_ID))
                .thenReturn(Optional.of(fromBudget));
        when(budgetRepository
                .findByUserAccountEntityUserIdAndCategoryEntityCategoryIdAndBudgetId(transferFunds.userId(),
                        toCategory.getCategoryId(), TestConstants.TRANSFER_TO_BUDGET_ID))
                .thenReturn(Optional.of(toBudget));

        //WHEN, THEN
        BackendException exception = assertThrows(BackendException.class, () ->
                categoryService
                        .transferFundsBetweenCategories(transferFunds));
        assertEquals(MessageConstants.INSUFFICIENT_FUNDS, exception.getMessage());
    }
}