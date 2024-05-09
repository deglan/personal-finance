package com.example.finance.mapper;

import com.example.finance.constants.ApplicationConstants;
import com.example.finance.model.dto.TransactionDto;
import com.example.finance.model.entity.CategoryEntity;
import com.example.finance.model.entity.TransactionsEntity;
import com.example.finance.model.entity.UserAccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = ApplicationConstants.SPRING_COMPONENT_MODEL, uses = {UserAccountMapper.class, CategoryMapper.class})
public interface TransactionMapper {

    @Named("toDto")
    @Mapping(source = "userAccountEntity.userId", target = "userId")
    @Mapping(source = "categoryEntity.categoryId", target = "categoryId")
    TransactionDto toDto(TransactionsEntity transactionsEntity);

    @Named("toEntity")
    @Mapping(source = "userId", target = "userAccountEntity", qualifiedByName = "userIdToUser")
    @Mapping(source = "categoryId", target = "categoryEntity", qualifiedByName = "IdToCategoryEntity")
    TransactionsEntity toEntity(TransactionDto transactionDto);

    @Named("userIdToUser")
    default UserAccountEntity userIdToUser(UUID userId) {
        if (userId == null) {
            return null;
        }
        UserAccountEntity user = new UserAccountEntity();
        user.setUserId(userId);
        return user;
    }

    @Named("IdToCategoryEntity")
    default CategoryEntity IdToCategoryEntity(UUID categoryId) {
        if (categoryId == null) {
            return null;
        }
        CategoryEntity category = new CategoryEntity();
        category.setCategoryId(categoryId);
        return category;
    }
}
