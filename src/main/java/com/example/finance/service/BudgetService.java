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


    public BudgetDto create(BudgetDto budget) {
        UserAccountEntity userAccountEntity = userAccountRepository.findById(budget.userId())
                .orElseThrow(() -> new BackendException("User not found"));
        CategoryEntity categoryEntity = categoriesRepository.findById(budget.categoryId())
                .orElseThrow(() -> new BackendException("Category not found"));
        BudgetEntity budgetEntity = BudgetEntity.builder()
                .userAccountEntity(userAccountEntity)
                .categoryEntity(categoryEntity)
                .amount(budget.amount())
                .month(budget.month())
                .year(budget.year())
                .build();
        budgetRepository.save(budgetEntity);
        log.debug(String.format("Created Budget with id %s and amount %s",
                budgetEntity.getBudgetId(),
                budgetEntity.getAmount()));
        return budgetMapper.toDto(budgetEntity);
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

    //TODO wróć do delete
    public void deleteBudget(UUID id) {
        BudgetEntity budgetEntity = budgetRepository.findById(id)
                .orElseThrow(() -> new BackendException(BUDGET_NOT_FOUND_EXCEPTION_MESSAGE + id));
        budgetRepository.delete(budgetEntity);
//        entityManager.flush();
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
