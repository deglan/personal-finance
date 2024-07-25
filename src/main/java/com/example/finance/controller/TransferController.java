package com.example.finance.controller;

import com.example.finance.business.ApiEndpoints;
import com.example.finance.model.dto.TransferFunds;
import com.example.finance.service.TransferService;
import com.example.finance.utils.MessageConstants;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class TransferController {
    private final TransferService transferService;

    @PostMapping(ApiEndpoints.Endpoints.TRANSFER)
    public ResponseEntity<String> transferFunds(@RequestBody @Valid TransferFunds request) {
        transferService.transferFundsBetweenCategories(request);
        return ResponseEntity.ok(MessageConstants.FUNDS_TRANSFERRED_SUCCESSFULLY);
    }
}
