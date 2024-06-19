package com.example.finance.repository;

import com.example.finance.model.entity.CategoryEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoriesRepository extends JpaRepository<CategoryEntity, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<CategoryEntity> findByIdWithLock(UUID id);
    List<CategoryEntity> findByUserAccountEntityUserId(UUID userId);
    Optional<CategoryEntity> findByUserAccountEntityUserIdAndName(UUID userId, String name);
}
