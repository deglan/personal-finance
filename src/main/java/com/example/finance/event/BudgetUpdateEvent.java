package com.example.finance.event;

import com.example.finance.model.entity.BudgetEntity;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class BudgetUpdateEvent extends ApplicationEvent {

    private final BudgetEntity budget;

    public BudgetUpdateEvent(Object source, BudgetEntity budget) {
        super(source);
        this.budget = budget;
    }
}
