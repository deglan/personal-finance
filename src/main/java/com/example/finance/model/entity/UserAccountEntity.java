package com.example.finance.model.entity;

import com.example.finance.auth.RoleConverter;
import com.example.finance.model.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "USER_ACCOUNT")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString(exclude = {"categoryEntities", "budgetEntities", "transactionsEntities", "reportEntities"})
public class UserAccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "USER_ID")
    private UUID userId;
    @Column(name = "LOGIN", unique = true, length = 50, nullable = false)
    private String login;
    @Column(name = "PASSWORD")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;
    @Column(name = "CREATED_DATE")
    @CreatedDate
    private LocalDateTime createdDate;
    @Column(name = "LAST_LOGIN")
    private LocalDateTime lastLogin;
    @OneToMany(mappedBy = "userAccountEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CategoryEntity> categoryEntities;
    @OneToMany(mappedBy = "userAccountEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BudgetEntity> budgetEntities;
    @OneToMany(mappedBy = "userAccountEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransactionsEntity> transactionsEntities;
    @OneToMany(mappedBy = "userAccountEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReportEntity> reportEntities;
    @Column(name = "ROLE")
    @Convert(converter = RoleConverter.class)
    private List<UserRole> role;
    @Column(name = "DELETED")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Builder.Default
    private boolean deleted = false;
    @Column(name = "ACTIVE")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Builder.Default
    private boolean active = true;
}
