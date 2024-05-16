package com.example.finance.mapper;

import com.example.finance.constants.ApplicationConstants;
import com.example.finance.model.dto.ReportDto;
import com.example.finance.model.entity.ReportEntity;
import com.example.finance.model.entity.UserAccountEntity;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper(componentModel = ApplicationConstants.SPRING_COMPONENT_MODEL, uses = UserAccountMapper.class)
public interface ReportMapper {

    @Named("toReportDto")
    @Mapping(source = "userAccountEntity.userId", target = "userId")
    ReportDto toDto(ReportEntity reportEntity);

    @IterableMapping(qualifiedByName = "toReportDto")
    List<ReportDto> toDtoList(List<ReportEntity> reportEntities);

    @Named("toReportEntity")
    @Mapping(target = "userAccountEntity", source = "userId", qualifiedByName = "userIdToUserAccountEntity")
    ReportEntity toEntity(ReportDto reportDto);

    @IterableMapping(qualifiedByName = "toReportEntity")
    List<ReportEntity> toEntityList(List<ReportDto> reportDtos);

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
