package com.example.finance.repository;

import com.example.finance.model.entity.UserAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccountEntity, Long> {

    Optional<UserAccountEntity> findByLoginAndActiveTrueAndDeletedFalse(String login);
}
