package com.example.finance.service.cache;

import com.example.finance.model.entity.UserAccountEntity;
import com.example.finance.repository.UserAccountRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@AllArgsConstructor
public class UserServiceCacheManager {

    private final UserAccountRepository userAccountRepository;
    private static final String CACHE_NAME = "users";

    @Cacheable(value = CACHE_NAME, key = "#uuid")
    public Optional<UserAccountEntity> findById(UUID uuid) {
        return userAccountRepository.findById(uuid);
    }

    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public void clearAllCache() {
        log.info("User cache cleared all data");
    }

    @CacheEvict(value = CACHE_NAME, key = "#uuid")
    public void clearUserCacheById(UUID uuid) {
        log.info("Cleared data from Cache for user with id " + uuid);
    }

    @CachePut(value = CACHE_NAME, key = "#uuid")
    public Optional<UserAccountEntity> updateUserCacheById(UUID uuid) {
        return userAccountRepository.findById(uuid);
    }
}
