package com.example.finance.repository;

import com.example.finance.model.entity.UserAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

public interface UserAccountRepository extends JpaRepository<UserAccountEntity, UUID> {

    Optional<UserAccountEntity> findByLoginAndActiveTrueAndDeletedFalse(String login);
    Optional<UserAccountEntity> findByEmail(String username);
}
