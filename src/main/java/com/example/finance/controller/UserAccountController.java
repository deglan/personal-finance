package com.example.finance.controller;

import com.example.finance.auth.TokenService;
import com.example.finance.business.ApiEndpoints;
import com.example.finance.model.dto.*;
import com.example.finance.model.entity.UserAccountEntity;
import com.example.finance.service.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(ApiEndpoints.Endpoints.USER)
@AllArgsConstructor
public class UserAccountController {

    private final UserAccountService userAccountService;
    private final CategoryService categoryService;
    private final BudgetService budgetService;
    private final TransactionService transactionService;
    private final ReportService reportService;
    private final TokenService tokenService;

    @PostMapping(value = ApiEndpoints.Endpoints.LOGIN, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<UserAccountDto> login(@RequestParam String login, @RequestParam String password) {
        UserAccountDto byLogin = userAccountService.getByLoginAndPassword(login, password);
        String bearerToken = tokenService.generateBearerToken(byLogin);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, bearerToken)
                .body(byLogin);
    }

    @GetMapping(value = ApiEndpoints.Endpoints.GET_ALL)
    public ResponseEntity<List<UserAccountDto>> getAll() {
        List<UserAccountDto> allUsers = userAccountService.getAll();
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping(value = ApiEndpoints.Endpoints.GET_BY_ID)
    public ResponseEntity<UserAccountDto> getById(@RequestParam UUID id) {
        UserAccountDto userById = userAccountService.getById(id);
        return ResponseEntity.ok(userById);
    }

    @GetMapping(value = ApiEndpoints.Endpoints.GET_CATEGORIES_BY_USER_ID)
    public ResponseEntity<List<CategoryDto>> getCategoriesByUserId(@PathVariable UUID id) {
        List<CategoryDto> byUserId = categoryService.getByUserId(id);
        return ResponseEntity.ok(byUserId);
    }

    @GetMapping(value = ApiEndpoints.Endpoints.GET_TRANSACTIONS_BY_USER_ID)
    public ResponseEntity<List<TransactionDto>> getTransactionsByUserId(@PathVariable UUID id) {
        List<TransactionDto> byUserId = transactionService.getByUserId(id);
        return ResponseEntity.ok(byUserId);
    }

    @GetMapping(value = ApiEndpoints.Endpoints.GET_BUDGETS_BY_USER_ID)
    public ResponseEntity<List<BudgetDto>> getBudgetsByUserId(@PathVariable UUID id) {
        List<BudgetDto> byUserId = budgetService.getByUserId(id);
        return ResponseEntity.ok(byUserId);
    }

    @GetMapping(value = ApiEndpoints.Endpoints.GET_REPORTS_BY_USER_ID)
    public ResponseEntity<List<ReportDto>> getReportsByUserId(@PathVariable UUID id) {
        List<ReportDto> byUserId = reportService.getByUserId(id);
        return ResponseEntity.ok(byUserId);
    }

    @PostMapping(value = ApiEndpoints.Endpoints.CREATE)
    public ResponseEntity<UserAccountDto> create(@RequestBody UserAccountEntity userAccount) {
        UserAccountDto userAccountDto = userAccountService.create(userAccount);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userAccountDto);
    }

    @PutMapping(value = ApiEndpoints.Endpoints.ID)
    public ResponseEntity<UserAccountDto> update(@PathVariable UUID id, @RequestBody UserAccountEntity userAccount) {
        UserAccountDto userAccountDto = userAccountService.updateUser(id, userAccount);
        return ResponseEntity.ok(userAccountDto);
    }

    @DeleteMapping(value = ApiEndpoints.Endpoints.ID)
    public ResponseEntity<UserAccountDto> delete(@PathVariable UUID id) {
        UserAccountDto userAccountDto = userAccountService.deleteUser(id);
        return ResponseEntity.ok(userAccountDto);
    }
}
