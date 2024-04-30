package com.example.finance.mapper;

import com.example.finance.constants.ApplicationConstants;
import com.example.finance.model.dto.UserAccountDto;
import com.example.finance.model.entity.UserAccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = ApplicationConstants.SPRING_COMPONENT_MODEL, uses = RoleMapper.class)
public interface UserAccountMapper {

    @Mapping(source = "rolesEntities", target = "roles")
    @Named("entityToDto")
    UserAccountDto toDto(UserAccountEntity userAccountEntity);

    @Mapping(source = "roles", target = "rolesEntities")
    @Named("dtoToEntity")
    UserAccountEntity toEntity(UserAccountDto userAccountDto);
}
