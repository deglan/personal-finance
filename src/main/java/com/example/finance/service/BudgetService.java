package com.example.finance.service;

import com.example.finance.event.BudgetUpdateEvent;
import com.example.finance.exception.model.BackendException;
import com.example.finance.mapper.BudgetMapper;
import com.example.finance.mapper.CategoryMapper;
import com.example.finance.mapper.UserAccountMapper;
import com.example.finance.model.dto.BudgetDto;
import com.example.finance.model.dto.UserAccountDto;
import com.example.finance.model.entity.BudgetEntity;
import com.example.finance.model.entity.CategoryEntity;
import com.example.finance.model.entity.UserAccountEntity;
import com.example.finance.repository.BudgetRepository;
import com.example.finance.repository.UserAccountRepository;
import com.example.finance.utils.MessageConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
@Slf4j
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;
    private final UserAccountService userAccountService;
    private final UserAccountMapper userAccountMapper;
    private final BudgetMapper budgetMapper;
    private final ApplicationEventPublisher applicationEventPublisher;

    public List<BudgetDto> getByUserId(UUID userId) {
        return budgetMapper.toDtoList(budgetRepository.findByUserAccountEntityUserId(userId));
    }

    public BudgetDto getById(UUID id) {
        return budgetRepository.findById(id)
                .map(budgetMapper::toDto)
                .orElseThrow(() -> new BackendException(MessageConstants.BUDGET_NOT_FOUND_EXCEPTION_MESSAGE + id));
    }

    public BudgetEntity getByUserIdAndCategoryIdAndBudgetId(UUID userId, UUID categoryId, UUID budgetId) {
        return budgetRepository
                .findByUserAccountEntityUserIdAndCategoryEntityCategoryIdAndBudgetId(userId,
                        categoryId,
                        budgetId)
                .orElseThrow(() -> new BackendException(MessageConstants.SOURCE_BUDGET));
    }

    @Transactional
    public BudgetDto create(BudgetDto budgetDto) {
        UserAccountDto userAccountDto = userAccountService.getById(budgetDto.userId());
        UserAccountEntity userAccountEntity = userAccountMapper.toEntity(userAccountDto);
        CategoryEntity categoryEntity = categoryMapper.toEntity(categoryService.getById(budgetDto.categoryId()));
        BudgetEntity budgetEntity = budgetMapper.toEntity(budgetDto);
        budgetEntity.setUserAccountEntity(userAccountEntity);
        budgetEntity.setCategoryEntity(categoryEntity);
        BudgetEntity savedEntity = budgetRepository.save(budgetEntity);
        log.debug(String.format("Created Budget with id %s and amount %s",
                savedEntity.getBudgetId(),
                savedEntity.getAmount()));
        return budgetMapper.toDto(savedEntity);
    }

    public void saveAll(List<BudgetEntity> list) {
        budgetRepository.saveAll(list);
    }

    @Transactional
    public BudgetDto updateBudget(BudgetDto budgetDto) {
        BudgetEntity budgetDb = budgetMapper.toEntity(budgetDto);
        BudgetEntity savedBudget = budgetRepository.saveAndFlush(budgetDb);
        applicationEventPublisher.publishEvent(new BudgetUpdateEvent(this, savedBudget));
        return budgetMapper.toDto(savedBudget);
    }

    @Transactional
    public void deleteBudget(UUID id) {
        budgetRepository.findById(id)
                .orElseThrow(() -> new BackendException(MessageConstants.BUDGET_NOT_FOUND_EXCEPTION_MESSAGE + id));
        budgetRepository.deleteById(id);
    }

    //Used in aspect programming
    public boolean existById(UUID id) {
        return budgetRepository.existsById(id);
    }
}
