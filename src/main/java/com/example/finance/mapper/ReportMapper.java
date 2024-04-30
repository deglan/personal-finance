package com.example.finance.mapper;

import com.example.finance.constants.ApplicationConstants;
import com.example.finance.model.dto.ReportDto;
import com.example.finance.model.entity.ReportEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = ApplicationConstants.SPRING_COMPONENT_MODEL, uses = UserAccountMapper.class)
public interface ReportMapper {

    @Named("toDto")
    ReportDto toDto(ReportEntity reportEntity);

    @Named("toEntity")
    ReportEntity toEntity(ReportDto reportDto);
}
