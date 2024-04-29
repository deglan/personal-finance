package com.example.finance.mapper;

import com.example.finance.constants.ApplicationConstants;
import com.example.finance.model.dto.CategoryDto;
import com.example.finance.model.dto.UserAccountDto;
import com.example.finance.model.entity.CategoryEntity;
import com.example.finance.model.entity.UserAccountEntity;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = ApplicationConstants.SPRING_COMPONENT_MODEL)
public interface CategoryMapper {

    @Named("toDto")
    CategoryDto toDto(CategoryEntity categoryEntity);

    @Named("toEntity")
    CategoryEntity toEntity(CategoryDto categoryDto);

    @IterableMapping(qualifiedByName = "toDto")
    List<CategoryDto> toListDto(List<CategoryEntity> categoryEntities);

    @IterableMapping(qualifiedByName = "toEntity")
    List<CategoryEntity> toListEntity(List<CategoryDto> categoryDtos);
}
