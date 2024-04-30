package com.example.finance.model.entity;

import com.example.finance.model.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "TRANSACTIONS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TRANSACTION_ID")
    private UUID transactionID;
    @OneToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private UserAccountEntity userAccountEntity;
    @OneToOne
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
