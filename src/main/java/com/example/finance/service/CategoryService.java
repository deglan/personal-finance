package com.example.finance.service;

import com.example.finance.exception.model.BackendException;
import com.example.finance.mapper.CategoryMapper;
import com.example.finance.model.dto.CategoryDto;
import com.example.finance.model.entity.CategoryEntity;
import com.example.finance.model.entity.UserAccountEntity;
import com.example.finance.repository.CategoriesRepository;
import com.example.finance.repository.UserAccountRepository;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class CategoryService {

    private static final String MESSAGE = "There is no such category";

    private final CategoriesRepository categoriesRepository;
    private final UserAccountRepository userAccountRepository;
    private final CategoryMapper categoryMapper;
    private final EntityManager entityManager;

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

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public CategoryDto create(CategoryDto category) {
        UserAccountEntity userAccountEntity = userAccountRepository.findById(category.userId())
                .orElseThrow(() -> new BackendException("User not found"));
        CategoryEntity categoryEntity = CategoryEntity.builder()
                .name(category.name())
                .transactionType(category.transactionType())
                .description(category.description())
                .userAccountEntity(userAccountEntity)
                .build();
        categoriesRepository.save(categoryEntity);

        log.info("Saved category with ID {} and type {} for user with ID {}",
                categoryEntity.getCategoryId(),
                categoryEntity.getTransactionType(),
                categoryEntity.getUserAccountEntity().getUserId());

        return categoryMapper.toDto(categoryEntity);
    }

    @Transactional
    public CategoryDto updateCategory(UUID id, CategoryEntity categoryEntity) {
        CategoryEntity categoryDb = categoriesRepository.findById(id)
                .orElseThrow(() -> new BackendException(MESSAGE));
        categoryDb.setName(categoryEntity.getName());
        categoryDb.setTransactionType(categoryEntity.getTransactionType());
        categoryDb.setDescription(categoryEntity.getDescription());
        categoriesRepository.save(categoryDb);
        return categoryMapper.toDto(categoryDb);
    }

    //TODO wróć do delete
    public void deleteCategory(UUID id) {
        categoriesRepository.deleteById(id);
    }

    private List<CategoryDto> toDtoList(List<CategoryEntity> categoryEntities) {
        return categoryEntities.stream()
                .map(categoryMapper::toDto)
                .toList();
    }
}
