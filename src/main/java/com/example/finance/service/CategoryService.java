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

    private final CategoriesRepository categoriesRepository;
    private final UserAccountService userAccountService;
    private final CategoryMapper categoryMapper;
    private final UserAccountMapper userAccountMapper;

    public List<CategoryDto> getAll() {
        return categoryMapper.toDtoList(categoriesRepository.findAll());
    }

    public List<CategoryDto> getByUserId(UUID userId) {
        return categoryMapper.toDtoList(categoriesRepository.findByUserAccountEntityUserId(userId));
    }

    public CategoryDto getById(UUID id) {
        return categoriesRepository.findById(id)
                .map(categoryMapper::toDto)
                .orElseThrow(() -> new BackendException(MessageConstants.CATEGORY_NOT_FOUND));
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
    public CategoryDto updateCategory(CategoryDto category) {
        CategoryEntity categoryEntity = categoriesRepository.findByIdWithLock(category.categoryId())
                .orElseThrow(() -> new BackendException(MessageConstants.CATEGORY_NOT_FOUND));
        CategoryEntity categoryDb = categoryMapper.toEntity(category);
        categoryDb.setBudgetEntities(categoryEntity.getBudgetEntities());
        categoryDb.setTransactionsEntities(categoryEntity.getTransactionsEntities());
        CategoryEntity savedCategory = categoriesRepository.save(categoryDb);
        return categoryMapper.toDto(savedCategory);
    }

    public CategoryEntity getByUserIdAndCategoryName(UUID userId, String name) {
        return categoriesRepository
                .findByUserAccountEntityUserIdAndName(userId, name)
                .orElseThrow(() -> new BackendException(MessageConstants.SOURCE_CATEGORY));
    }

    @Transactional
    public void deleteCategory(UUID id) {
        CategoryEntity categoryEntity = categoriesRepository.findById(id)
                .orElseThrow(() -> new BackendException(MessageConstants.CATEGORY_NOT_FOUND));
        categoriesRepository.delete(categoryEntity);
    }

    // AOP
    public boolean existById(UUID id) {
        return categoriesRepository.existsById(id);
    }
}
