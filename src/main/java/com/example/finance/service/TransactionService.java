package com.example.finance.service;

import com.example.finance.exception.model.BackendException;
import com.example.finance.mapper.TransactionMapper;
import com.example.finance.model.dto.TransactionDto;
import com.example.finance.model.entity.CategoryEntity;
import com.example.finance.model.entity.TransactionsEntity;
import com.example.finance.model.entity.UserAccountEntity;
import com.example.finance.repository.CategoriesRepository;
import com.example.finance.repository.TransactionsRepository;
import com.example.finance.repository.UserAccountRepository;
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

    private static final String MESSAGE = "There is no such category";

    private final TransactionsRepository transactionsRepository;
    private final UserAccountRepository userAccountRepository;
    private final TransactionMapper transactionMapper;
    private final CategoriesRepository categoriesRepository;


    public List<TransactionDto> getByUserId(UUID userId) {
        return transactionMapper.toDtoList(transactionsRepository.findByUserAccountEntityUserId(userId));
    }

    public TransactionDto getById(UUID id) {
        return transactionsRepository.findById(id)
                .map(transactionMapper::toDto)
                .orElseThrow(() -> new BackendException(MESSAGE));
    }

    @Transactional
    public TransactionDto create(TransactionDto transaction) {
        UserAccountEntity userAccountEntity = userAccountRepository.findById(transaction.userId())
                .orElseThrow(() -> new BackendException("User not found"));
        CategoryEntity categoryEntity = categoriesRepository.findById(transaction.categoryId())
                .orElseThrow(() -> new BackendException("Category not found"));
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
        TransactionsEntity savedTransaction = transactionsRepository.save(transactionDb);
        return transactionMapper.toDto(savedTransaction);
    }

    @Transactional
    public void deleteTransaction(UUID id) {
        TransactionsEntity transactionsEntity = transactionsRepository.findById(id)
                .orElseThrow(() -> new BackendException(MESSAGE));
        transactionsRepository.delete(transactionsEntity);
    }
}
