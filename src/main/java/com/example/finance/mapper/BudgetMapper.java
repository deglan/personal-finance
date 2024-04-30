package com.example.finance.mapper;

import com.example.finance.constants.ApplicationConstants;
import com.example.finance.model.dto.BudgetDto;
import com.example.finance.model.entity.BudgetEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = ApplicationConstants.SPRING_COMPONENT_MODEL, uses = {UserAccountMapper.class, CategoryMapper.class})
public interface BudgetMapper {

    @Mapping(target = "userAccountDto", source = "userAccountEntity")
    @Mapping(target = "categoryDtos", source = "categoryEntity")
    @Named("toDto")
    BudgetDto toDto(BudgetEntity budgetEntity);

    @Mapping(target = "userAccountEntity", source = "userAccountDto")
    @Mapping(target = "categoryEntity", source = "categoryDtos")
    @Named("toEntity")
    BudgetEntity toEntity(BudgetDto budgetDto);
}
