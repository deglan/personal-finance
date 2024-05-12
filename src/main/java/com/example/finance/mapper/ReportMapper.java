package com.example.finance.mapper;

import com.example.finance.constants.ApplicationConstants;
import com.example.finance.model.dto.ReportDto;
import com.example.finance.model.entity.ReportEntity;
import com.example.finance.model.entity.UserAccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Optional;
import java.util.UUID;

@Mapper(componentModel = ApplicationConstants.SPRING_COMPONENT_MODEL, uses = UserAccountMapper.class)
public interface ReportMapper {

    @Named("toDto")
    @Mapping(source = "userAccountEntity.userId", target = "userId")
    ReportDto toDto(ReportEntity reportEntity);

    @Named("toEntity")
    @Mapping(target = "userAccountEntity", source = "userId", qualifiedByName = "userIdToUserAccountEntity")
    ReportEntity toEntity(ReportDto reportDto);

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
