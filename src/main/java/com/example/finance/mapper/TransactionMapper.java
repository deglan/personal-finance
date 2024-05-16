package com.example.finance.mapper;

import com.example.finance.constants.ApplicationConstants;
import com.example.finance.model.dto.TransactionDto;
import com.example.finance.model.entity.CategoryEntity;
import com.example.finance.model.entity.TransactionsEntity;
import com.example.finance.model.entity.UserAccountEntity;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper(componentModel = ApplicationConstants.SPRING_COMPONENT_MODEL, uses = {UserAccountMapper.class, CategoryMapper.class})
public interface TransactionMapper {

    @Named("toDto")
    @Mapping(source = "userAccountEntity.userId", target = "userId")
    @Mapping(source = "categoryEntity.categoryId", target = "categoryId")
    TransactionDto toDto(TransactionsEntity transactionsEntity);

    @IterableMapping(qualifiedByName = "toDto")
    List<TransactionDto> toDtoList(List<TransactionsEntity> transactionsEntities);


    @Named("toEntity")
    @Mapping(source = "userId", target = "userAccountEntity", qualifiedByName = "userIdToUser")
    @Mapping(source = "categoryId", target = "categoryEntity", qualifiedByName = "IdToCategoryEntity")
    TransactionsEntity toEntity(TransactionDto transactionDto);

    @IterableMapping(qualifiedByName = "toEntity")
    List<TransactionsEntity> toEntityList(List<TransactionDto> transactionDtos);

    @Named("userIdToUser")
    default UserAccountEntity userIdToUser(UUID userId) {
        return Optional.ofNullable(userId)
                .map(id -> {
                    UserAccountEntity user = new UserAccountEntity();
                    user.setUserId(id);
                    return user;
                })
                .orElse(null);
    }

    @Named("IdToCategoryEntity")
    default CategoryEntity IdToCategoryEntity(UUID categoryId) {
        return Optional.ofNullable(categoryId)
                .map(id -> {
                    CategoryEntity category = new CategoryEntity();
                    category.setCategoryId(id);
                    return category;
                })
                .orElse(null);
    }
}
