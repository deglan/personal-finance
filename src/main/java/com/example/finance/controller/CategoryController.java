package com.example.finance.controller;

import com.example.finance.business.ApiEndpoints;
import com.example.finance.model.dto.CategoryDto;
import com.example.finance.model.dto.TransferFunds;
import com.example.finance.service.CategoryService;
import com.example.finance.utils.MessageConstants;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(ApiEndpoints.Endpoints.API + ApiEndpoints.Endpoints.CATEGORIES)
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

    @GetMapping(value = ApiEndpoints.Endpoints.GET_CATEGORIES_BY_USER_ID)
    public ResponseEntity<List<CategoryDto>> getCategoriesByUserId(@PathVariable UUID id) {
        List<CategoryDto> categoriesByUserId = categoryService.getByUserId(id);
        return ResponseEntity.ok(categoriesByUserId);
    }

    @PostMapping(value = ApiEndpoints.Endpoints.CREATE)
    public ResponseEntity<CategoryDto> create(@RequestBody CategoryDto category) {
        CategoryDto categoryDto = categoryService.create(category);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryDto);
    }

    @PostMapping(ApiEndpoints.Endpoints.TRANSFER)
    public ResponseEntity<String> transferFunds(@RequestBody @Valid TransferFunds request) {
        categoryService.transferFundsBetweenCategories(request);
        return ResponseEntity.ok(MessageConstants.FUNDS_TRANSFERRED_SUCCESSFULLY);
    }

    @PutMapping(value = ApiEndpoints.Endpoints.ID)
    public ResponseEntity<CategoryDto> update(@PathVariable UUID id, @RequestBody CategoryDto category) {
        CategoryDto categoryDto = categoryService.updateCategory(id, category);
        return ResponseEntity.ok(categoryDto);
    }

    @DeleteMapping(value = ApiEndpoints.Endpoints.ID)
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
