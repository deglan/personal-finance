package com.example.finance.factory;

import com.example.finance.model.dto.BudgetDto;
import com.example.finance.model.entity.BudgetEntity;
import com.example.finance.model.entity.CategoryEntity;
import com.example.finance.model.entity.UserAccountEntity;
import com.example.finance.utils.TestConstants;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BudgetMockFactory {

    public BudgetEntity createBudgetEntity(UserAccountEntity user, CategoryEntity category) {
        return BudgetEntity.builder()
                .budgetId(TestConstants.BUDGET_UUID)
                .userAccountEntity(user)
                .categoryEntity(category)
                .amount(TestConstants.BUDGET_AMOUNT)
                .month(TestConstants.BUDGET_MONTH)
                .year(TestConstants.BUDGET_YEAR)
                .build();
    }

    public BudgetEntity createBudgetEntity() {
        UserAccountEntity userEntity = UserMockFactory.createUserEntity();
        return BudgetEntity.builder()
                .budgetId(TestConstants.BUDGET_UUID)
                .userAccountEntity(userEntity)
                .categoryEntity(CategoryMockFactory.createCategoryEntityWithUser(userEntity))
                .amount(TestConstants.BUDGET_AMOUNT)
                .month(TestConstants.BUDGET_MONTH)
                .year(TestConstants.BUDGET_YEAR)
                .build();
    }


    public BudgetEntity createBudgetEntity(int month) {
        UserAccountEntity userEntity = UserMockFactory.createUserEntity();
        return BudgetEntity.builder()
                .budgetId(TestConstants.BUDGET_UUID)
                .userAccountEntity(userEntity)
                .categoryEntity(CategoryMockFactory.createCategoryEntityWithUser(userEntity))
                .amount(TestConstants.BUDGET_AMOUNT)
                .month(month)
                .year(TestConstants.BUDGET_YEAR)
                .build();
    }

    public BudgetEntity createBudgetEntityWithoutUUID() {
        UserAccountEntity userEntity = UserMockFactory.createUserEntity();
        return BudgetEntity.builder()
                .budgetId(null)
                .userAccountEntity(userEntity)
                .categoryEntity(CategoryMockFactory.createCategoryEntityWithUser(userEntity))
                .amount(TestConstants.BUDGET_AMOUNT)
                .month(TestConstants.BUDGET_MONTH)
                .year(TestConstants.BUDGET_YEAR)
                .build();
    }

    public BudgetDto createBudgetDto() {
        return new BudgetDto(
                TestConstants.BUDGET_UUID,
                TestConstants.USER_UUID,
                TestConstants.CATEGORY_UUID,
                TestConstants.BUDGET_AMOUNT,
                TestConstants.BUDGET_MONTH,
                TestConstants.BUDGET_YEAR
        );
    }

    public BudgetDto createBudgetDto(int month) {
        return new BudgetDto(
                TestConstants.BUDGET_UUID,
                TestConstants.USER_UUID,
                TestConstants.CATEGORY_UUID,
                TestConstants.BUDGET_AMOUNT,
                month,
                TestConstants.BUDGET_YEAR
        );
    }

    public BudgetDto createBudgetDtoWithoutUUID() {
        return new BudgetDto(
                null,
                TestConstants.USER_UUID,
                TestConstants.CATEGORY_UUID,
                TestConstants.BUDGET_AMOUNT,
                TestConstants.BUDGET_MONTH,
                TestConstants.BUDGET_YEAR
        );
    }
}
