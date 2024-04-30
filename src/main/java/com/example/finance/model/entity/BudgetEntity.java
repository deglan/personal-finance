package com.example.finance.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "BUDGET")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BudgetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "BUDGET_ID")
    private UUID budgetId;
    @OneToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private UserAccountEntity userAccountEntity;
    @OneToMany(mappedBy = "categoryId", fetch = FetchType.EAGER)
    private Set<CategoryEntity> categoryEntity;
    @Column(name = "AMOUNT", nullable = false)
    private BigDecimal amount;
    @Column(name = "MONTH", nullable = false)
    private Integer month;
    @Column(name = "YEAR", nullable = false)
    private Integer year;
}