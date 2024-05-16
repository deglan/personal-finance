package com.example.finance.model.entity;

import com.example.finance.model.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "TRANSACTIONS")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"userAccountEntity", "categoryEntity"})
public class TransactionsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "TRANSACTION_ID")
    private UUID transactionID;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private UserAccountEntity userAccountEntity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID", nullable = false)
    private CategoryEntity categoryEntity;
    @Column(name = "AMOUNT", nullable = false)
    private BigDecimal amount;
    @Column(name = "TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @Column(name = "DATE", nullable = false)
    private LocalDate date;
    @Column(name = "DESCRIPTION")
    private String description;
}
