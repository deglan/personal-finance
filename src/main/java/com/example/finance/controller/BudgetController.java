package com.example.finance.controller;

import com.example.finance.business.ApiEndpoints;
import com.example.finance.model.dto.BudgetDto;
import com.example.finance.service.BudgetService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(ApiEndpoints.Endpoints.BUDGET)
@AllArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    @GetMapping(ApiEndpoints.Endpoints.ID)
    public ResponseEntity<BudgetDto> getById(@PathVariable @NotNull(message = "Budget not found") UUID id) {
        return ResponseEntity.ok(budgetService.getById(id));
    }

    @PostMapping(ApiEndpoints.Endpoints.CREATE)
    public ResponseEntity<BudgetDto> create(@RequestBody @Valid BudgetDto budgetDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(budgetService.create(budgetDto));
    }

    @PutMapping(value = ApiEndpoints.Endpoints.ID)
    public ResponseEntity<BudgetDto> update(@PathVariable UUID id, @RequestBody BudgetDto transaction) {
        BudgetDto budgetDto = budgetService.updateBudget(id, transaction);
        return ResponseEntity.ok(budgetDto);
    }

    @DeleteMapping(value = ApiEndpoints.Endpoints.ID)
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        budgetService.deleteBudget(id);
        return ResponseEntity.noContent().build();
    }
}
