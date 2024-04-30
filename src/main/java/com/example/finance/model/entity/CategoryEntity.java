package com.example.finance.model.entity;

import com.example.finance.model.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "CATEGORY")
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    @Column(name = "DESCRIPTION")
    private String description;
}
