package com.example.finance.controller;

import com.example.finance.business.ApiEndpoints;
import com.example.finance.model.dto.CategoryDto;
import com.example.finance.model.entity.CategoryEntity;
import com.example.finance.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(ApiEndpoints.Endpoints.CATEGORIES)
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping(value = ApiEndpoints.Endpoints.GET_ALL)
    public ResponseEntity<List<CategoryDto>> getAll() {
        List<CategoryDto> allCategories = categoryService.getAll();
        return ResponseEntity.ok(allCategories);
    }

    @GetMapping(value = ApiEndpoints.Endpoints.ID)
    public ResponseEntity<CategoryDto> getById(@PathVariable UUID id) {
        CategoryDto categoryById = categoryService.getById(id);
        return ResponseEntity.ok(categoryById);
    }

    @PostMapping(value = ApiEndpoints.Endpoints.CREATE)
    public ResponseEntity<CategoryDto> create(@RequestBody CategoryDto category) {
        CategoryDto categoryDto = categoryService.create(category);
        return ResponseEntity.ok(categoryDto);
    }

    @PutMapping(value = ApiEndpoints.Endpoints.ID)
    public ResponseEntity<CategoryDto> update(@PathVariable UUID id, @RequestBody CategoryEntity categoryEntity) {
        CategoryDto categoryDto = categoryService.updateCategory(id, categoryEntity);
        return ResponseEntity.ok(categoryDto);
    }

    @DeleteMapping(value = ApiEndpoints.Endpoints.ID)
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

}
