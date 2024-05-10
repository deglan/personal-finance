package com.example.finance.repository;

import com.example.finance.model.entity.CategoryEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoriesRepositoryTest {

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Test
    @Transactional
    @DirtiesContext
    public void testDeleteCategory() {
        List<CategoryEntity> all = categoriesRepository.findAll();
        CategoryEntity first = all.getFirst();
        categoriesRepository.deleteById(first.getCategoryId());
        categoriesRepository.flush();
        assertFalse(categoriesRepository.existsById(first.getCategoryId()));
    }
}