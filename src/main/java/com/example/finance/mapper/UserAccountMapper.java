package com.example.finance.mapper;

import com.example.finance.constants.ApplicationConstants;
import com.example.finance.model.dto.UserAccountDto;
import com.example.finance.model.entity.UserAccountEntity;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.*;

@Mapper(componentModel = ApplicationConstants.SPRING_COMPONENT_MODEL, uses = RoleMapper.class)
public interface UserAccountMapper {
//TODO refleksa i mappery
    @Mapping(source = "rolesEntities", target = "roles")
    @Named("entityToDto")
    UserAccountDto toDto(UserAccountEntity userAccountEntity);

    @Mapping(source = "roles", target = "rolesEntities")
    @Named("dtoToEntity")
    UserAccountEntity toEntity(UserAccountDto userAccountDto);

    @IterableMapping(qualifiedByName = "entityToDto")
    List<UserAccountDto> toListDto(List<UserAccountEntity> userAccountEntity);

    @IterableMapping(qualifiedByName = "dtoToEntity")
    List<UserAccountEntity> toListEntity(List<UserAccountDto> userAccountDtos);
}
