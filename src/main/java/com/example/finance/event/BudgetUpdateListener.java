package com.example.finance.event;

import com.example.finance.model.entity.BudgetEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
public class BudgetUpdateListener {

    @TransactionalEventListener(BudgetUpdateEvent.class)
    public void onBudgetUpdateEvent(BudgetUpdateEvent budgetUpdateEvent) {
        BudgetEntity budget = budgetUpdateEvent.getBudget();
        log.info("Updated budget with id: " + budget.getBudgetId());
    }
}
