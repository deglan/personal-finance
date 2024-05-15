package com.example.finance.service;

import com.example.finance.exception.model.BackendException;
import com.example.finance.mapper.BudgetMapper;
import com.example.finance.model.dto.BudgetDto;
import com.example.finance.model.entity.BudgetEntity;
import com.example.finance.model.entity.CategoryEntity;
import com.example.finance.model.entity.UserAccountEntity;
import com.example.finance.repository.BudgetRepository;
import com.example.finance.repository.CategoriesRepository;
import com.example.finance.repository.UserAccountRepository;
import com.example.finance.utils.MessageConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
@Slf4j
public class BudgetService {

    private final String BUDGET_NOT_FOUND_EXCEPTION_MESSAGE = "No budget found for id: ";

    private final BudgetRepository budgetRepository;
    private final CategoriesRepository categoriesRepository;
    private final UserAccountRepository userAccountRepository;
    private final BudgetMapper budgetMapper;

    public List<BudgetDto> getByUserId(UUID userId) {
        return toDtoList(budgetRepository.findByUserAccountEntityUserId(userId));
    }

    public BudgetDto getById(UUID id) {
        return budgetRepository.findById(id)
                .map(budgetMapper::toDto)
                .orElseThrow(() -> new BackendException(BUDGET_NOT_FOUND_EXCEPTION_MESSAGE + id));
    }

    public BudgetDto create(BudgetDto budgetDto) {
        UserAccountEntity userAccountEntity = userAccountRepository.findById(budgetDto.userId())
                .orElseThrow(() -> new BackendException(MessageConstants.USER_NOT_FOUND));
        CategoryEntity categoryEntity = categoriesRepository.findById(budgetDto.categoryId())
                .orElseThrow(() -> new BackendException(MessageConstants.CATEGORY_NOT_FOUND));
        BudgetEntity budgetEntity = budgetMapper.toEntity(budgetDto);
        budgetEntity.setUserAccountEntity(userAccountEntity);
        budgetEntity.setCategoryEntity(categoryEntity);
        BudgetEntity savedEntity = budgetRepository.save(budgetEntity);
        log.debug(String.format("Created Budget with id %s and amount %s",
                savedEntity.getBudgetId(),
                savedEntity.getAmount()));
        return budgetMapper.toDto(savedEntity);
    }

    @Transactional
    public BudgetDto updateBudget(UUID id, BudgetDto budgetDto) {
        BudgetEntity budgetDb = budgetRepository.findById(id)
                .orElseThrow(() -> new BackendException(BUDGET_NOT_FOUND_EXCEPTION_MESSAGE + id));
        budgetDb.setAmount(budgetDto.amount());
        budgetDb.setMonth(budgetDb.getMonth());
        budgetDb.setYear(budgetDb.getYear());
        budgetRepository.save(budgetDb);
        return budgetMapper.toDto(budgetDb);
    }

    public void deleteBudget(UUID id) {
        budgetRepository.findById(id)
                        .orElseThrow(() -> new BackendException(BUDGET_NOT_FOUND_EXCEPTION_MESSAGE + id));
        budgetRepository.deleteById(id);
    }

    private List<BudgetDto> toDtoList(List<BudgetEntity> budgetEntities) {
        return budgetEntities.stream()
                .map(budgetMapper::toDto)
                .toList();
    }

    public BudgetDto toDto(BudgetEntity budgetEntity) {
        return budgetMapper.toDto(budgetEntity);
    }
}
