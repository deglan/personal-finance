package com.example.finance.mapper;

import com.example.finance.constants.ApplicationConstants;
import com.example.finance.model.dto.CategoryDto;
import com.example.finance.model.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = ApplicationConstants.SPRING_COMPONENT_MODEL)
public interface CategoryMapper {

    @Named("toDto")
    CategoryDto toDto(CategoryEntity categoryEntity);

    @Named("toEntity")
    CategoryEntity toEntity(CategoryDto categoryDto);
}
