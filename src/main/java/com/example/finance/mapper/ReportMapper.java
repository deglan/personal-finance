package com.example.finance.mapper;

import com.example.finance.constants.ApplicationConstants;
import com.example.finance.model.dto.ReportDto;
import com.example.finance.model.dto.RoleDto;
import com.example.finance.model.entity.ReportEntity;
import com.example.finance.model.entity.RoleEntity;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = ApplicationConstants.SPRING_COMPONENT_MODEL, uses = UserAccountMapper.class)
public interface ReportMapper {

    @Named("toDto")
    ReportDto toDto(ReportEntity reportEntity);

    @Named("toEntity")
    ReportEntity toEntity(ReportDto reportDto);

    @IterableMapping(qualifiedByName = "toDto")
    List<ReportDto> toDtoList(List<ReportEntity> reportEntities);

    @IterableMapping(qualifiedByName = "toEntity")
    List<ReportEntity> toEntityList(List<ReportDto> reportDtos);
}
