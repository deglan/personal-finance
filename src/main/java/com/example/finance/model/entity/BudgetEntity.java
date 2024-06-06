package com.example.finance.model.entity;

import com.example.finance.listener.BudgetEntityListener;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "BUDGET")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(value = BudgetEntityListener.class)
@ToString(exclude = {"userAccountEntity"})
public class BudgetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "BUDGET_ID")
    private UUID budgetId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private UserAccountEntity userAccountEntity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID", nullable = false)
    private CategoryEntity categoryEntity;
    @Column(name = "AMOUNT", nullable = false)
    private BigDecimal amount;
    @Column(name = "MONTH", nullable = false)
    private Integer month;
    @Column(name = "YEAR", nullable = false)
    private Integer year;
}