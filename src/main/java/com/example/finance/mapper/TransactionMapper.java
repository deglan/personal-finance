package com.example.finance.mapper;

import com.example.finance.constants.ApplicationConstants;
import com.example.finance.model.dto.TransactionDto;
import com.example.finance.model.entity.TransactionsEntity;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.*;

@Mapper(componentModel = ApplicationConstants.SPRING_COMPONENT_MODEL, uses = {UserAccountMapper.class, CategoryMapper.class})
public interface TransactionMapper {

    @Named("toDto")
    TransactionDto toDto(TransactionsEntity transactionsEntity);

    @Named("toEntity")
    TransactionsEntity toEntity(TransactionDto transactionDto);

    @IterableMapping(qualifiedByName = "toDto")
    List<TransactionDto> toDtoList(List<TransactionsEntity> transactionsEntities);

    @IterableMapping(qualifiedByName = "toEntity")
    List<TransactionsEntity> toEntityList(List<TransactionDto> transactionDtos);
}
