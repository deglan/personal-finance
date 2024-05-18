package com.example.finance.utils;

import com.example.finance.model.dto.TransferFunds;
import com.example.finance.model.entity.BudgetEntity;
import com.example.finance.model.entity.CategoryEntity;
import com.example.finance.model.entity.UserAccountEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TransferFundsSetupHelper {

    UserAccountEntity user;
    CategoryEntity fromCategory;
    CategoryEntity toCategory;
    BudgetEntity fromBudget;
    BudgetEntity toBudget;
    TransferFunds transferFunds;
}
