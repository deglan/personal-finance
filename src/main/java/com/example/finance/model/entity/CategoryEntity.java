package com.example.finance.model.entity;

import com.example.finance.model.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "CATEGORY")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"userAccountEntity", "budgetEntities", "transactionsEntities"})
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "CATEGORY_ID", updatable = false, nullable = false)
    private UUID categoryId;
    @Column(name = "NAME", nullable = false)
    private String name;
    @Column(name = "TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private UserAccountEntity userAccountEntity;
    @OneToMany(mappedBy = "categoryEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransactionsEntity> transactionsEntities;
    @OneToMany(mappedBy = "categoryEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BudgetEntity> budgetEntities;
    @Column(name = "DESCRIPTION")
    private String description;
}
