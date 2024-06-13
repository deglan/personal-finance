package com.example.finance.service;

import com.example.finance.exception.model.BackendException;
import com.example.finance.mapper.CategoryMapper;
import com.example.finance.mapper.UserAccountMapper;
import com.example.finance.model.dto.CategoryDto;
import com.example.finance.model.dto.TransferFunds;
import com.example.finance.model.dto.UserAccountDto;
import com.example.finance.model.entity.BudgetEntity;
import com.example.finance.model.entity.CategoryEntity;
import com.example.finance.model.entity.UserAccountEntity;
import com.example.finance.repository.CategoriesRepository;
import com.example.finance.utils.MessageConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class CategoryService {

    private static final String MESSAGE = "There is no such category";

    private final CategoriesRepository categoriesRepository;
    private final UserAccountService userAccountService;
    private final BudgetService budgetService;
    private final CategoryMapper categoryMapper;
    private final UserAccountMapper userAccountMapper;

    public List<CategoryDto> getAll() {
        return toDtoList(categoriesRepository.findAll());
    }

    public List<CategoryDto> getByUserId(UUID userId) {
        return toDtoList(categoriesRepository.findByUserAccountEntityUserId(userId));
    }

    public CategoryDto getById(UUID id) {
        return categoriesRepository.findById(id)
                .map(categoryMapper::toDto)
                .orElseThrow(() -> new BackendException(MESSAGE));
    }


    @Transactional
    public CategoryDto create(CategoryDto category) {
        UserAccountDto userAccountDto = userAccountService.getById(category.userId());
        UserAccountEntity userAccountEntity = userAccountMapper.toEntity(userAccountDto);
        CategoryEntity categoryEntity = categoryMapper.toEntity(category);
        categoryEntity.setUserAccountEntity(userAccountEntity);
        CategoryEntity savedCategory = categoriesRepository.save(categoryEntity);
        log.info("Saved category with ID {} and type {} for user with ID {}",
                categoryEntity.getCategoryId(),
                categoryEntity.getTransactionType(),
                categoryEntity.getUserAccountEntity().getUserId());
        return categoryMapper.toDto(savedCategory);
    }

    @Transactional
    public CategoryDto updateCategory(UUID id, CategoryDto category) {
        CategoryEntity existingCategory = categoriesRepository.findById(id)
                .orElseThrow(() -> new BackendException(MESSAGE));
        CategoryEntity categoryDb = categoryMapper.toEntity(category);
        categoryDb.setCategoryId(existingCategory.getCategoryId());
        CategoryEntity savedCategory = categoriesRepository.save(categoryDb);
        return categoryMapper.toDto(savedCategory);
    }

    public CategoryEntity getByUserIdAndCategoryName(UUID userId, String name) {
        return categoriesRepository
                .findByUserAccountEntityUserIdAndName(userId, name)
                .orElseThrow(() -> new BackendException(MessageConstants.SOURCE_CATEGORY));
    }

    @Transactional
    public void transferFundsBetweenCategories(TransferFunds transferFunds) {
        CategoryEntity fromCategory = getByUserIdAndCategoryName(transferFunds.userId(),
                transferFunds.fromCategoryName());
        CategoryEntity toCategory = getByUserIdAndCategoryName(transferFunds.userId(),
                transferFunds.toCategoryName());
        BudgetEntity fromBudget = budgetService.getByUserIdAndCategoryIdAndBudgetId(transferFunds.userId(),
                fromCategory.getCategoryId(),
                transferFunds.fromBudgetId());
        BudgetEntity toBudget = budgetService.getByUserIdAndCategoryIdAndBudgetId(transferFunds.userId(),
                toCategory.getCategoryId(),
                transferFunds.toBudgetId());
        BigDecimal transferFundsAmount = transferFunds.amount();
        BigDecimal fromBudgetAmount = fromBudget.getAmount();
        BigDecimal toBudgetAmount = toBudget.getAmount();
        if (fromBudgetAmount.compareTo(transferFundsAmount) < 0) {
            throw new BackendException(MessageConstants.INSUFFICIENT_FUNDS);
        }
        BigDecimal newFromBudget = fromBudgetAmount.subtract(transferFundsAmount);
        BigDecimal newToBudget = toBudgetAmount.add(transferFundsAmount);
        fromBudget.setAmount(newFromBudget);
        toBudget.setAmount(newToBudget);
        budgetService.saveAll(List.of(fromBudget, toBudget));
    }

    @Transactional
    public void deleteCategory(UUID id) {
        CategoryEntity categoryEntity = categoriesRepository.findById(id)
                .orElseThrow(() -> new BackendException(MESSAGE));
        categoriesRepository.delete(categoryEntity);
    }

    private List<CategoryDto> toDtoList(List<CategoryEntity> categoryEntities) {
        return categoryEntities.stream()
                .map(categoryMapper::toDto)
                .toList();
    }
}
