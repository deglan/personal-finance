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

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(ApiEndpoints.Endpoints.API + ApiEndpoints.Endpoints.BUDGET)
@AllArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    @GetMapping(ApiEndpoints.Endpoints.ID)
    public ResponseEntity<BudgetDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(budgetService.getById(id));
    }

    @GetMapping(value = ApiEndpoints.Endpoints.GET_BUDGETS_BY_USER_ID)
    public ResponseEntity<List<BudgetDto>> getBudgetsByUserId(@PathVariable UUID id) {
        List<BudgetDto> budgetsByUserId = budgetService.getByUserId(id);
        return ResponseEntity.ok(budgetsByUserId);
    }

    @PostMapping(ApiEndpoints.Endpoints.CREATE)
    public ResponseEntity<BudgetDto> create(@RequestBody BudgetDto budgetDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(budgetService.create(budgetDto));
    }

    @PutMapping(value = ApiEndpoints.Endpoints.ID)
    public ResponseEntity<BudgetDto> update(@PathVariable UUID id, @RequestBody BudgetDto budget) {
        BudgetDto budgetDto = budgetService.updateBudget(budget);
        return ResponseEntity.ok(budgetDto);
    }

    @DeleteMapping(value = ApiEndpoints.Endpoints.ID)
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        budgetService.deleteBudget(id);
        return ResponseEntity.noContent().build();
    }
}
