package com.example.finance.repository;

import com.example.finance.model.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoriesRepository extends JpaRepository<CategoryEntity, UUID> {

    List<CategoryEntity> findByUserAccountEntityUserId(UUID userId);

    Optional<CategoryEntity> findByUserAccountEntityUserIdAndName(UUID userId, String name);
}
