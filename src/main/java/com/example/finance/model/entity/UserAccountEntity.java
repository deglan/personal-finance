package com.example.finance.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "USER_ACCOUNT")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserAccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    @Column(name = "DELETED")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean deleted;
    @Column(name = "ACTIVE")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean active;
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "USER_ROLE",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID")
    )
    private Set<RoleEntity> rolesEntities;
}
