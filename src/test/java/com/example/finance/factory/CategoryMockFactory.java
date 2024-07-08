package com.example.finance.factory;

import com.example.finance.model.dto.CategoryDto;
import com.example.finance.model.entity.CategoryEntity;
import com.example.finance.model.entity.UserAccountEntity;
import com.example.finance.model.enums.TransactionType;
import com.example.finance.utils.TestConstants;
import lombok.experimental.UtilityClass;

import java.util.*;

@UtilityClass
public class CategoryMockFactory {

    public CategoryEntity createCategoryEntityWithUser(UserAccountEntity user) {
        return CategoryEntity.builder()
                .categoryId(TestConstants.CATEGORY_UUID)
                .name(TestConstants.CATEGORY_NAME)
                .transactionType(TransactionType.EXPENSE)
                .userAccountEntity(user)
                .transactionsEntities(Collections.emptyList())
                .budgetEntities(Collections.emptyList())
                .description(TestConstants.CATEGORY_DESCRIPTION)
                .build();
    }

    public CategoryEntity createCategoryEntity() {
        return CategoryEntity.builder()
                .categoryId(TestConstants.CATEGORY_UUID)
                .name(TestConstants.CATEGORY_NAME)
                .transactionType(TransactionType.EXPENSE)
                .userAccountEntity(UserMockFactory.createUserEntity())
                .transactionsEntities(Collections.emptyList())
                .budgetEntities(Collections.emptyList())
                .description(TestConstants.CATEGORY_DESCRIPTION)
                .build();
    }

    public List<CategoryEntity> createCategoryEntities() {
        return List.of(createCategoryEntity());
    }

    public CategoryDto createCategoryDto() {
        return new CategoryDto(
                TestConstants.CATEGORY_UUID,
                TestConstants.USER_UUID,
                TestConstants.CATEGORY_NAME,
                TransactionType.EXPENSE,
                TestConstants.CATEGORY_DESCRIPTION
        );
    }

    public CategoryDto createCategoryDtoWithoutUUID() {
        return new CategoryDto(
                null,
                TestConstants.USER_UUID,
                TestConstants.CATEGORY_NAME,
                TransactionType.EXPENSE,
                TestConstants.CATEGORY_DESCRIPTION
        );
    }

    public List<CategoryDto> createCategoryDtos() {
        return List.of(createCategoryDto());
    }
}
