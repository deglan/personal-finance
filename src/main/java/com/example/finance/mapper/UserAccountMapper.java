package com.example.finance.mapper;

import com.example.finance.constants.ApplicationConstants;
import com.example.finance.model.dto.UserAccountDto;
import com.example.finance.model.entity.UserAccountEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = ApplicationConstants.SPRING_COMPONENT_MODEL)
public interface UserAccountMapper {

    UserAccountDto toDto(UserAccountEntity userAccountEntity);

    UserAccountEntity toEntity(UserAccountDto userAccountDto);
}
