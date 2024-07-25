package com.example.finance.service;

import com.example.finance.exception.model.BackendException;
import com.example.finance.mapper.CategoryMapper;
import com.example.finance.mapper.TransactionMapper;
import com.example.finance.mapper.UserAccountMapper;
import com.example.finance.model.dto.CategoryDto;
import com.example.finance.model.dto.TransactionDto;
import com.example.finance.model.dto.UserAccountDto;
import com.example.finance.model.entity.CategoryEntity;
import com.example.finance.model.entity.TransactionsEntity;
import com.example.finance.model.entity.UserAccountEntity;
import com.example.finance.repository.TransactionsRepository;
import com.example.finance.utils.MessageConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class TransactionService {

    private final TransactionsRepository transactionsRepository;
    private final UserAccountService userAccountService;
    private final UserAccountMapper userAccountMapper;
    private final TransactionMapper transactionMapper;
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;


    public List<TransactionDto> getByUserId(UUID userId) {
        return transactionMapper.toDtoList(transactionsRepository.findByUserAccountEntityUserId(userId));
    }

    public TransactionDto getById(UUID id) {
        return transactionsRepository.findById(id)
                .map(transactionMapper::toDto)
                .orElseThrow(() -> new BackendException(MessageConstants.TRANSACTION_NOT_FOUND));
    }

    @Transactional
    public TransactionDto create(TransactionDto transaction) {
        UserAccountDto userAccountDto = userAccountService.getById(transaction.userId());
        UserAccountEntity userAccountEntity = userAccountMapper.toEntity(userAccountDto);
        CategoryDto categoryDto = categoryService.getById(transaction.categoryId());
        CategoryEntity categoryEntity = categoryMapper.toEntity(categoryDto);
        TransactionsEntity transactionsEntity = transactionMapper.toEntity(transaction);
        transactionsEntity.setUserAccountEntity(userAccountEntity);
        transactionsEntity.setCategoryEntity(categoryEntity);
        TransactionsEntity savedTransaction = transactionsRepository.save(transactionsEntity);
        log.info("Saved transaction with ID {} and type {} for user with ID {}",
                savedTransaction.getTransactionId(),
                savedTransaction.getTransactionType(),
                savedTransaction.getUserAccountEntity().getUserId());
        return transactionMapper.toDto(savedTransaction);
    }

    @Transactional
    public TransactionDto updateTransaction(TransactionDto transaction) {
        TransactionsEntity transactionDb = transactionMapper.toEntity(transaction);
        TransactionsEntity savedTransaction = transactionsRepository.saveAndFlush(transactionDb);
        return transactionMapper.toDto(savedTransaction);
    }

    @Transactional
    public void deleteTransaction(UUID id) {
        TransactionsEntity transactionsEntity = transactionsRepository.findById(id)
                .orElseThrow(() -> new BackendException(MessageConstants.TRANSACTION_NOT_FOUND));
        transactionsRepository.delete(transactionsEntity);
    }

    // AOP
    public boolean existById(UUID id) {
        return transactionsRepository.existsById(id);
    }
}
