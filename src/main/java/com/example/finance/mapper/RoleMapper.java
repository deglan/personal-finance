package com.example.finance.mapper;

import com.example.finance.constants.ApplicationConstants;
import com.example.finance.model.dto.RoleDto;
import com.example.finance.model.entity.RoleEntity;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.*;

@Mapper(componentModel = ApplicationConstants.SPRING_COMPONENT_MODEL)
public interface RoleMapper {

    @Named("toDto")
    RoleDto toDto(RoleEntity roleEntity);

    @Named("toEntity")
    RoleEntity toEntity(RoleDto roleDto);

    @IterableMapping(qualifiedByName = "toDto")
    List<RoleDto> toDtoList(List<RoleEntity> roleEntities);

    @IterableMapping(qualifiedByName = "toEntity")
    List<RoleEntity> toEntityList(List<RoleDto> roleDtos);
}
