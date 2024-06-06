package com.example.finance.listener;

import com.example.finance.model.entity.BudgetEntity;
import jakarta.persistence.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
public class BudgetEntityListener {

    private final org.slf4j.Logger log;

    public BudgetEntityListener(org.slf4j.Logger log) {
        this.log = log;
    }

    @PrePersist
    public void prePersist(BudgetEntity budgetEntity) {
        budgetEntity.setYear(2000);
    }

    @PostPersist
    public void postPersist(BudgetEntity budgetEntity) {
        log.info("Budget created with id " + budgetEntity.getBudgetId());
    }

    @PreUpdate
    public void preUpdate(BudgetEntity budgetEntity) {
        budgetEntity.setYear(1000);
    }

    @PostUpdate
    public void postUpdate(BudgetEntity budgetEntity) {
        log.info("Budget updated with id " + budgetEntity.getBudgetId());
    }

    @PreRemove
    public void preRemove(BudgetEntity budgetEntity) {
        log.info("deleting budget");
    }

    @PostRemove
    public void postRemove(BudgetEntity budgetEntity) {
        log.info("Deleted budget with id: " + budgetEntity.getBudgetId());
    }
}
