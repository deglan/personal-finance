package com.example.finance.service;

import com.example.finance.exception.model.AuthorizationException;
import com.example.finance.exception.model.BackendException;
import com.example.finance.mapper.UserAccountMapper;
import com.example.finance.model.dto.UserAccountDto;
import com.example.finance.model.entity.UserAccountEntity;
import com.example.finance.repository.UserAccountRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class UserAccountService {

    protected static final String MESSAGE = "There is no such user";

    private final UserAccountRepository userAccountRepository;
    private final UserAccountMapper userAccountMapper;
    private final PasswordEncoder passwordEncoder;

    public UserAccountDto getByLoginAndPassword(String login, String password) {
        UserAccountEntity userAccountEntity = userAccountRepository.findByLoginAndActiveTrueAndDeletedFalse(login)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .orElseThrow(() -> new AuthorizationException(MESSAGE));
        updateLastLogin(userAccountEntity);
        return userAccountMapper.toDto(userAccountEntity);
    }

    public List<UserAccountDto> getAll() {
        return userAccountRepository.findAll().stream()
                .map(userAccountMapper::toDto)
                .toList();
    }

    public UserAccountDto getById(UUID id) {
        UserAccountEntity user = userAccountRepository.findById(id)
                .orElseThrow(() -> new BackendException(MESSAGE));
        return userAccountMapper.toDto(user);
    }

    public UserAccountDto create(UserAccountEntity userAccount) {
        String encodedPassword = passwordEncoder.encode(userAccount.getPassword());
        userAccount.setPassword(encodedPassword);
        userAccount.setCreatedDate(LocalDateTime.now());
        UserAccountEntity userDb = userAccountRepository.save(userAccount);
        log.info(String.format("Saved user with ID %s and login %s", userDb.getUserId(), userDb.getLogin()));
        return userAccountMapper.toDto(userDb);
    }

    public UserAccountDto updateUser(UUID id, UserAccountEntity userAccountEntity) {
        UserAccountEntity userAccountDb = userAccountRepository.findById(id)
                .orElseThrow(() -> new BackendException(MESSAGE));
        String encodedPassword = passwordEncoder.encode(userAccountEntity.getPassword());
        userAccountDb.setPassword(encodedPassword);
        userAccountDb.setLogin(userAccountEntity.getLogin());
        userAccountDb.setEmail(userAccountEntity.getEmail());
        userAccountRepository.save(userAccountDb);
        return userAccountMapper.toDto(userAccountDb);
    }

    public UserAccountDto deleteUser(UUID id) {
        UserAccountEntity userAccountDb = userAccountRepository.findById(id)
                .orElseThrow(() -> new BackendException(MESSAGE));
        setDeleteUser(userAccountDb);
        userAccountRepository.save(userAccountDb);
        return userAccountMapper.toDto(userAccountDb);
    }

    private UserAccountEntity updateLastLogin(UserAccountEntity userAccountEntity) {
        userAccountEntity.setLastLogin(LocalDateTime.now());
        userAccountRepository.save(userAccountEntity);
        return userAccountEntity;
    }

    private UserAccountEntity setDeleteUser(UserAccountEntity userAccountEntity) {
        userAccountEntity.setDeleted(true);
        userAccountEntity.setActive(false);
        userAccountRepository.save(userAccountEntity);
        return userAccountEntity;
    }
}
