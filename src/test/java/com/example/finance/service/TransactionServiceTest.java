package com.example.finance.service;

import com.example.finance.exception.model.BackendException;
import com.example.finance.factory.CategoryMockFactory;
import com.example.finance.factory.TransactionMockFactory;
import com.example.finance.factory.UserMockFactory;
import com.example.finance.mapper.TransactionMapper;
import com.example.finance.model.dto.TransactionDto;
import com.example.finance.model.entity.CategoryEntity;
import com.example.finance.model.entity.TransactionsEntity;
import com.example.finance.model.entity.UserAccountEntity;
import com.example.finance.repository.CategoriesRepository;
import com.example.finance.repository.TransactionsRepository;
import com.example.finance.repository.UserAccountRepository;
import com.example.finance.utils.MessageConstants;
import com.example.finance.utils.TestConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionsRepository transactionsRepository;

    @Mock
    private UserAccountRepository userAccountRepository;

    @Mock
    private TransactionMapper transactionMapper;

    @Mock
    private CategoriesRepository categoriesRepository;

    @InjectMocks
    private TransactionService transactionService;

    private static final Logger log = LoggerFactory.getLogger(TransactionServiceTest.class);

    private TransactionDto transactionDto;
    private TransactionsEntity transactionsEntity;
    private UserAccountEntity userAccountEntity;
    private CategoryEntity categoryEntity;

    @BeforeEach
    void setUp() {
        transactionDto = TransactionMockFactory.createTransactionDtoFullData();
        transactionsEntity = TransactionMockFactory.createTransactionEntity();
        userAccountEntity = UserMockFactory.createUserEntity();
        categoryEntity = CategoryMockFactory.createCategoryEntity();
    }

    @Test
    void shouldReturnTransactionsByUserId() {
        // Given
        List<TransactionsEntity> transactionsEntities = List.of(transactionsEntity);
        List<TransactionDto> transactionDtos = List.of(transactionDto);

        when(transactionsRepository.findByUserAccountEntityUserId(TestConstants.USER_UUID)).thenReturn(transactionsEntities);
        when(transactionMapper.toDtoList(transactionsEntities)).thenReturn(transactionDtos);

        // When
        List<TransactionDto> result = transactionService.getByUserId(TestConstants.USER_UUID);

        // Then
        assertThat(result).isEqualTo(transactionDtos);
    }

    @Test
    void shouldReturnTransactionById() {
        // Given
        when(transactionsRepository.findById(TestConstants.TRANSACTION_UUID)).thenReturn(Optional.of(transactionsEntity));
        when(transactionMapper.toDto(transactionsEntity)).thenReturn(transactionDto);

        // When
        TransactionDto result = transactionService.getById(TestConstants.TRANSACTION_UUID);

        // Then
        assertThat(result).isEqualTo(transactionDto);
    }

    @Test
    void shouldThrowBackendExceptionWhenTransactionNotFoundById() {
        // Given
        when(transactionsRepository.findById(TestConstants.TRANSACTION_UUID)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> transactionService.getById(TestConstants.TRANSACTION_UUID))
                .isInstanceOf(BackendException.class)
                .hasMessageContaining(MessageConstants.TRANSACTION_NOT_FOUND);
    }

    @Test
    void shouldCreateTransaction() {
        // Given
        when(userAccountRepository.findById(transactionDto.userId())).thenReturn(Optional.of(userAccountEntity));
        when(categoriesRepository.findById(transactionDto.categoryId())).thenReturn(Optional.of(categoryEntity));
        when(transactionMapper.toEntity(transactionDto)).thenReturn(transactionsEntity);
        when(transactionsRepository.save(transactionsEntity)).thenReturn(transactionsEntity);
        when(transactionMapper.toDto(transactionsEntity)).thenReturn(transactionDto);

        // When
        TransactionDto result = transactionService.create(transactionDto);

        // Then
        assertThat(result).isEqualTo(transactionDto);
        verify(transactionsRepository).save(transactionsEntity);
    }

    @Test
    void shouldThrowBackendExceptionWhenUserNotFoundForCreate() {
        // Given
        when(userAccountRepository.findById(transactionDto.userId())).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> transactionService.create(transactionDto))
                .isInstanceOf(BackendException.class)
                .hasMessageContaining("User not found");
    }

    @Test
    void shouldThrowBackendExceptionWhenCategoryNotFoundForCreate() {
        // Given
        when(userAccountRepository.findById(transactionDto.userId())).thenReturn(Optional.of(userAccountEntity));
        when(categoriesRepository.findById(transactionDto.categoryId())).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> transactionService.create(transactionDto))
                .isInstanceOf(BackendException.class)
                .hasMessageContaining("Category not found");
    }

    @Test
    void shouldUpdateTransaction() {
        // Given
        when(transactionMapper.toEntity(transactionDto)).thenReturn(transactionsEntity);
        when(transactionsRepository.saveAndFlush(transactionsEntity)).thenReturn(transactionsEntity);
        when(transactionMapper.toDto(transactionsEntity)).thenReturn(transactionDto);

        // When
        TransactionDto result = transactionService.updateTransaction(transactionDto);

        // Then
        assertThat(result).isEqualTo(transactionDto);
        verify(transactionsRepository).saveAndFlush(transactionsEntity);
    }

    @Test
    void shouldDeleteTransaction() {
        // Given
        when(transactionsRepository.findById(TestConstants.TRANSACTION_UUID)).thenReturn(Optional.of(transactionsEntity));

        // When
        transactionService.deleteTransaction(TestConstants.TRANSACTION_UUID);

        // Then
        verify(transactionsRepository).delete(transactionsEntity);
    }

    @Test
    void shouldThrowBackendExceptionWhenTransactionNotFoundForDelete() {
        // Given
        when(transactionsRepository.findById(TestConstants.TRANSACTION_UUID)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> transactionService.deleteTransaction(TestConstants.TRANSACTION_UUID))
                .isInstanceOf(BackendException.class)
                .hasMessageContaining(MessageConstants.TRANSACTION_NOT_FOUND);
    }
}
