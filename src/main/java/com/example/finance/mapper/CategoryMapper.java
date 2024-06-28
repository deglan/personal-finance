package com.example.finance.mapper;

import com.example.finance.constants.ApplicationConstants;
import com.example.finance.model.dto.CategoryDto;
import com.example.finance.model.entity.CategoryEntity;
import com.example.finance.model.entity.UserAccountEntity;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.*;
import java.util.UUID;

@Mapper(componentModel = ApplicationConstants.SPRING_COMPONENT_MODEL, uses = {UserAccountMapper.class})
public interface CategoryMapper {

    @Named("toDto")
    @Mapping(source = "userAccountEntity.userId", target = "userId")
    CategoryDto toDto(CategoryEntity categoryEntity);

    @IterableMapping(qualifiedByName = "toDto")
    List<CategoryDto> toDtoList(List<CategoryEntity> categoryEntityList);

    @Named("toEntity")
    @Mapping(target = "userAccountEntity", source = "userId", qualifiedByName = "userIdToUserAccountEntity")
    CategoryEntity toEntity(CategoryDto categoryDto);

    @IterableMapping(qualifiedByName = "toEntity")
    List<CategoryEntity> toEntityList(List<CategoryDto> categoryDtoList);

    @Named("userIdToUserAccountEntity")
    default UserAccountEntity fromUserId(UUID userId) {
        return Optional.ofNullable(userId)
                .map(id -> {
                    UserAccountEntity user = new UserAccountEntity();
                    user.setUserId(id);
                    return user;
                })
                .orElse(null);
    }
}
