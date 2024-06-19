package com.example.finance.service;

import com.example.finance.exception.model.BackendException;
import com.example.finance.factory.BudgetMockFactory;
import com.example.finance.factory.CategoryMockFactory;
import com.example.finance.factory.TransferFundsMockFactory;
import com.example.finance.factory.UserMockFactory;
import com.example.finance.mapper.CategoryMapper;
import com.example.finance.mapper.UserAccountMapper;
import com.example.finance.model.dto.TransferFunds;
import com.example.finance.model.entity.BudgetEntity;
import com.example.finance.model.entity.CategoryEntity;
import com.example.finance.model.entity.UserAccountEntity;
import com.example.finance.repository.BudgetRepository;
import com.example.finance.repository.CategoriesRepository;
import com.example.finance.repository.UserAccountRepository;
import com.example.finance.utils.MessageConstants;
import com.example.finance.utils.TestConstants;
import com.example.finance.utils.TransferFundsSetupHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    CategoriesRepository categoriesRepository;
    @Mock
    UserAccountService userAccountService;
    @Mock
    BudgetService budgetService;
    @Mock
    CategoryMapper categoryMapper;
    @Mock
    UserAccountMapper userAccountMapper;
    CategoryService categoryService;

    @BeforeEach
    public void setUp() {
        categoryService = new CategoryService(categoriesRepository, userAccountService, budgetService, categoryMapper, userAccountMapper);
    }

    @Test
    void transferFunds_changeFundsBetweenCategories_success() {
        // GIVEN
        BigDecimal initialFromBudgetAmount = BigDecimal.valueOf(500.00);
        BigDecimal initialToBudgetAmount = BigDecimal.valueOf(300.00);
        TransferFundsSetupHelper helper = setupTransferFunds(initialFromBudgetAmount, initialToBudgetAmount);

        // WHEN
        categoryService.transferFundsBetweenCategories(helper.getTransferFunds());

        // THEN
        assertEquals(BigDecimal.valueOf(400.00), helper.getFromBudget().getAmount());
        assertEquals(BigDecimal.valueOf(400.00), helper.getToBudget().getAmount());
    }

    @Test
    void transferFunds_changeFundsBetweenCategories_insufficientFunds() {
        //GIVEN
        BigDecimal initialFromBudgetAmount = BigDecimal.valueOf(50.00);
        BigDecimal initialToBudgetAmount = BigDecimal.valueOf(300.00);
        TransferFundsSetupHelper helper = setupTransferFunds(initialFromBudgetAmount, initialToBudgetAmount);

        //WHEN, THEN
        BackendException exception = assertThrows(BackendException.class, () ->
                categoryService
                        .transferFundsBetweenCategories(helper.getTransferFunds()));
        assertEquals(MessageConstants.INSUFFICIENT_FUNDS, exception.getMessage());
    }

    private TransferFundsSetupHelper setupTransferFunds(BigDecimal fromBudgetAmount, BigDecimal toBudgetAmount) {
        UserAccountEntity user = UserMockFactory.createUserEntity();
        CategoryEntity fromCategory = CategoryMockFactory.createCategoryEntityWithUser(user);
        CategoryEntity toCategory = CategoryMockFactory.createCategoryEntityWithUser(user);
        BudgetEntity fromBudget = BudgetMockFactory.createBudgetEntity(user, fromCategory);
        BudgetEntity toBudget = BudgetMockFactory.createBudgetEntity(user, toCategory);
        TransferFunds transferFunds = TransferFundsMockFactory.createTransferFundsDto();

        fromBudget.setAmount(fromBudgetAmount);
        toBudget.setAmount(toBudgetAmount);

        when(categoriesRepository
                .findByUserAccountEntityUserIdAndName(transferFunds.userId(), transferFunds.fromCategoryName()))
                .thenReturn(Optional.of(fromCategory));
        when(categoriesRepository
                .findByUserAccountEntityUserIdAndName(transferFunds.userId(), transferFunds.toCategoryName()))
                .thenReturn(Optional.of(toCategory));
        when(budgetService.getByUserIdAndCategoryIdAndBudgetId(transferFunds.userId(),
                fromCategory.getCategoryId(), transferFunds.fromBudgetId()))
                .thenReturn(fromBudget);
        when(budgetService.getByUserIdAndCategoryIdAndBudgetId(transferFunds.userId(),
                toCategory.getCategoryId(), transferFunds.toBudgetId()))
                .thenReturn(toBudget);

        return new TransferFundsSetupHelper(user, fromCategory, toCategory, fromBudget, toBudget, transferFunds);
    }
}