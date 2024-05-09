package com.example.finance.controller;

import com.example.finance.business.ApiEndpoints;
import com.example.finance.model.dto.TransactionDto;
import com.example.finance.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(ApiEndpoints.Endpoints.TRANSACTION)
@AllArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping(value = ApiEndpoints.Endpoints.ID)
    public ResponseEntity<TransactionDto> getById(@PathVariable UUID id) {
        TransactionDto transactionById = transactionService.getById(id);
        return ResponseEntity.ok(transactionById);
    }

    @PostMapping(value = ApiEndpoints.Endpoints.CREATE)
    public ResponseEntity<TransactionDto> create(@RequestBody TransactionDto transaction) {
        TransactionDto transactionDto = transactionService.create(transaction);
        return ResponseEntity.ok(transactionDto);
    }

    @PutMapping(value = ApiEndpoints.Endpoints.ID)
    public ResponseEntity<TransactionDto> update(@PathVariable UUID id, @RequestBody TransactionDto transaction) {
        TransactionDto transactionDto = transactionService.updateTransaction(id, transaction);
        return ResponseEntity.ok(transactionDto);
    }

    @DeleteMapping(value = ApiEndpoints.Endpoints.ID)
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }
}
