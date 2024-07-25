package com.example.finance.service;

import com.example.finance.exception.model.BackendException;
import com.example.finance.model.dto.TransferFunds;
import com.example.finance.model.entity.BudgetEntity;
import com.example.finance.model.entity.CategoryEntity;
import com.example.finance.utils.MessageConstants;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class TransferService {

    private final CategoryService categoryService;
    private final BudgetService budgetService;


    @Transactional
    public void transferFundsBetweenCategories(TransferFunds transferFunds) {
        CategoryEntity fromCategory = categoryService.getByUserIdAndCategoryName(transferFunds.userId(),
                transferFunds.fromCategoryName());
        CategoryEntity toCategory = categoryService.getByUserIdAndCategoryName(transferFunds.userId(),
                transferFunds.toCategoryName());
        BudgetEntity fromBudget = budgetService.getByUserIdAndCategoryIdAndBudgetId(transferFunds.userId(),
                fromCategory.getCategoryId(),
                transferFunds.fromBudgetId());
        BudgetEntity toBudget = budgetService.getByUserIdAndCategoryIdAndBudgetId(transferFunds.userId(),
                toCategory.getCategoryId(),
                transferFunds.toBudgetId());
        BigDecimal transferFundsAmount = transferFunds.amount();
        BigDecimal fromBudgetAmount = fromBudget.getAmount();
        BigDecimal toBudgetAmount = toBudget.getAmount();
        if (fromBudgetAmount.compareTo(transferFundsAmount) < 0) {
            throw new BackendException(MessageConstants.INSUFFICIENT_FUNDS);
        }
        BigDecimal newFromBudget = fromBudgetAmount.subtract(transferFundsAmount);
        BigDecimal newToBudget = toBudgetAmount.add(transferFundsAmount);
        fromBudget.setAmount(newFromBudget);
        toBudget.setAmount(newToBudget);
        budgetService.saveAll(List.of(fromBudget, toBudget));
    }

}
