package com.example.finance.listener;

import com.example.finance.factory.BudgetMockFactory;
import com.example.finance.model.entity.BudgetEntity;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import static org.mockito.Mockito.verify;

@SpringBootTest
@ContextConfiguration(classes = {BudgetEntityListener.class})
class BudgetEntityListenerTest {

    @Autowired
    private BudgetEntityListener budgetEntityListener;

    @MockBean
    private Logger log;

    @Test
    void budgetListener_postPersist_success() {
        BudgetEntity budgetEntity = BudgetMockFactory.createBudgetEntity();
        budgetEntityListener.postPersist(budgetEntity);
        verify(log).info("Budget created with id " + budgetEntity.getBudgetId());
    }

    @Test
    void budgetListener_postUpdate_success() {
        BudgetEntity budgetEntity = BudgetMockFactory.createBudgetEntity();
        budgetEntityListener.postUpdate(budgetEntity);
        verify(log).info("Budget updated with id " + budgetEntity.getBudgetId());
    }

    @Test
    void budgetListener_preRemove_success() {
        BudgetEntity budgetEntity = BudgetMockFactory.createBudgetEntity();
        budgetEntityListener.preRemove(budgetEntity);
        verify(log).info("deleting budget");
    }

    @Test
    void budgetListener_postRemove_success() {
        BudgetEntity budgetEntity = BudgetMockFactory.createBudgetEntity();
        budgetEntityListener.postRemove(budgetEntity);
        verify(log).info("Deleted budget with id: " + budgetEntity.getBudgetId());
    }
}