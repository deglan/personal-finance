package com.example.finance.mapper;

import com.example.finance.constants.ApplicationConstants;
import com.example.finance.model.dto.BudgetDto;
import com.example.finance.model.entity.BudgetEntity;
import com.example.finance.model.entity.CategoryEntity;
import com.example.finance.model.entity.UserAccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = ApplicationConstants.SPRING_COMPONENT_MODEL, uses = {UserAccountMapper.class, CategoryMapper.class})
public interface BudgetMapper {

    @Named("toDto")
    @Mapping(source = "userAccountEntity.userId", target = "userId")
    @Mapping(source = "categoryEntity.categoryId", target = "categoryId")
    BudgetDto toDto(BudgetEntity budgetEntity);

    @Named("toEntity")
    @Mapping(source = "userId", target = "userAccountEntity", qualifiedByName = "userIdToUserAccount")
    @Mapping(source = "categoryId", target = "categoryEntity", qualifiedByName = "categoryIdToCategory")
    BudgetEntity toEntity(BudgetDto budgetDto);

    @Named("userIdToUserAccount")
    default UserAccountEntity userIdToUserAccount(UUID userId) {
        if (userId == null) {
            return null;
        }
        UserAccountEntity user = new UserAccountEntity();
        user.setUserId(userId);
        return user;
    }

    @Named("categoryIdToCategory")
    default CategoryEntity categoryIdToCategory(UUID categoryId) {
        if (categoryId == null) {
            return null;
        }
        CategoryEntity category = new CategoryEntity();
        category.setCategoryId(categoryId);
        return category;
    }
}
