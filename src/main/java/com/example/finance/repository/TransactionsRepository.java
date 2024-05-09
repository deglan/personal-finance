package com.example.finance.repository;

import com.example.finance.model.entity.TransactionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionsRepository extends JpaRepository<TransactionsEntity, UUID> {

    List<TransactionsEntity> findByUserAccountEntityUserId(UUID userId);
}
