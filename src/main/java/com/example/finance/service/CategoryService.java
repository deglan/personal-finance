package com.example.finance.service;

import com.example.finance.exception.model.BackendException;
import com.example.finance.mapper.CategoryMapper;
import com.example.finance.model.dto.CategoryDto;
import com.example.finance.model.dto.TransferFunds;
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
import org.springframework.transaction.annotation.Isolation;
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
    private final UserAccountRepository userAccountRepository;
    private final BudgetRepository budgetRepository;
    private final CategoryMapper categoryMapper;

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
        UserAccountEntity userAccountEntity = userAccountRepository.findById(category.userId())
                .orElseThrow(() -> new BackendException(MessageConstants.USER_NOT_FOUND));
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
        CategoryEntity categoryDb = categoriesRepository.findById(id)
                .orElseThrow(() -> new BackendException(MESSAGE));
        categoryDb.setName(category.name());
        categoryDb.setTransactionType(category.transactionType());
        categoryDb.setDescription(category.description());
        CategoryEntity savedCategory = categoriesRepository.save(categoryDb);
        return categoryMapper.toDto(savedCategory);
    }

    @Transactional
    public void transferFundsBetweenCategories(TransferFunds transferFunds) {
        CategoryEntity fromCategory = categoriesRepository
                .findByUserAccountEntityUserIdAndName(transferFunds.userId(), transferFunds.fromCategoryName())
                .orElseThrow(() -> new BackendException(MessageConstants.SOURCE_CATEGORY));
        CategoryEntity toCategory = categoriesRepository
                .findByUserAccountEntityUserIdAndName(transferFunds.userId(), transferFunds.toCategoryName())
                .orElseThrow(() -> new BackendException(MessageConstants.DESTINATION_CATEGORY));
        BudgetEntity fromBudget = budgetRepository
                .findByUserAccountEntityUserIdAndCategoryEntityCategoryIdAndBudgetId(transferFunds.userId(), fromCategory.getCategoryId(), transferFunds.fromBudgetId())
                .orElseThrow(() -> new BackendException(MessageConstants.SOURCE_BUDGET));
        BudgetEntity toBudget = budgetRepository
                .findByUserAccountEntityUserIdAndCategoryEntityCategoryIdAndBudgetId(transferFunds.userId(), toCategory.getCategoryId(), transferFunds.fromBudgetId())
                .orElseThrow(() -> new BackendException(MessageConstants.DESTINATION_BUDGET));
        BigDecimal fromAmount = fromBudget.getAmount();
        if (fromAmount.compareTo(transferFunds.amount()) < 0) {
            throw new BackendException(MessageConstants.INSUFFICIENT_FUNDS);
        }
        fromBudget.setAmount(fromBudget.getAmount().subtract(transferFunds.amount()));
        toBudget.setAmount(toBudget.getAmount().add(transferFunds.amount()));
        budgetRepository.saveAll(List.of(fromBudget, toBudget));
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
