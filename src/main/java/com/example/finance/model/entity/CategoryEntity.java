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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CATEGORY_ID", updatable = false, nullable = false)
    private UUID categoryId;
    @Column(name = "NAME", nullable = false)
    private String name;
    @Column(name = "TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "USER_ID")
    private UserAccountEntity userAccountEntity;
    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    private List<BudgetEntity> budgetEntities;
    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    private List<TransactionsEntity> transactionsEntities;
    @Column(name = "DESCRIPTION")
    private String description;
}
