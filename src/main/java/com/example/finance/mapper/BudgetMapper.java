package com.example.finance.mapper;

import com.example.finance.constants.ApplicationConstants;
import com.example.finance.model.dto.BudgetDto;
import com.example.finance.model.entity.BudgetEntity;
import com.example.finance.model.entity.CategoryEntity;
import com.example.finance.model.entity.UserAccountEntity;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.*;
import java.util.UUID;

@Mapper(componentModel = ApplicationConstants.SPRING_COMPONENT_MODEL, uses = {UserAccountMapper.class, CategoryMapper.class})
public interface BudgetMapper {

    @Named("toBudgetDto")
    @Mapping(source = "userAccountEntity.userId", target = "userId")
    @Mapping(source = "categoryEntity.categoryId", target = "categoryId")
    BudgetDto toDto(BudgetEntity budgetEntity);

    @IterableMapping(qualifiedByName = "toBudgetDto")
    List<BudgetDto> toDtoList(List<BudgetEntity> budgetEntities);

    @Named("toBudgetEntity")
    @Mapping(source = "userId", target = "userAccountEntity", qualifiedByName = "userIdToUserAccount")
    @Mapping(source = "categoryId", target = "categoryEntity", qualifiedByName = "categoryIdToCategory")
    BudgetEntity toEntity(BudgetDto budgetDto);

    @IterableMapping(qualifiedByName = "toBudgetEntity")
    List<BudgetEntity> toEntityList(List<BudgetDto> budgetEntities);

    @Named("userIdToUserAccount")
    default UserAccountEntity userIdToUserAccount(UUID userId) {
        return Optional.ofNullable(userId)
                .map(id -> {
                    UserAccountEntity user = new UserAccountEntity();
                    user.setUserId(id);
                    return user;
                })
                .orElse(null);
    }

    @Named("categoryIdToCategory")
    default CategoryEntity categoryIdToCategory(UUID categoryId) {
        return Optional.ofNullable(categoryId)
                .map(id -> {
                    CategoryEntity categoryEntity = new CategoryEntity();
                    categoryEntity.setCategoryId(id);
                    return categoryEntity;
                })
                .orElse(null);
    }
}
