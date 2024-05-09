package com.example.finance.service;

import com.example.finance.exception.model.BackendException;
import com.example.finance.mapper.UserAccountMapper;
import com.example.finance.model.dto.UserAccountDto;
import com.example.finance.model.entity.UserAccountEntity;
import com.example.finance.model.enums.UserRole;
import com.example.finance.repository.UserAccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserAccountServiceTest {

    @Mock
    private UserAccountRepository userAccountRepository;

    @Mock
    private UserAccountMapper userAccountMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserAccountService userAccountService;

    @Test
    void getByLoginAndPassword_Success() {
        String login = "user1";
        String password = "pass";
        UserAccountEntity entity = new UserAccountEntity();
        entity.setLogin(login);
        entity.setPassword(password);

        UUID userId = UUID.randomUUID();
        List<UserRole> roles = Collections.singletonList(UserRole.USER);

        when(userAccountRepository.findByLoginAndActiveTrueAndDeletedFalse(login))
                .thenReturn(Optional.of(entity));
        when(userAccountMapper.toDto(any())).thenReturn(new UserAccountDto(userId, login, roles));

        assertDoesNotThrow(() -> userAccountService.getByLoginAndPassword(login, password));
    }

    @Test
    void create_Success() {
        UserAccountEntity entity = new UserAccountEntity();
        entity.setPassword("password");
        entity.setLogin("newUser");
        entity.setEmail("newUser@example.com");

        UUID userId = UUID.randomUUID();
        List<UserRole> roles = Collections.singletonList(UserRole.USER);

        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userAccountRepository.save(any(UserAccountEntity.class))).thenReturn(entity);
        when(userAccountMapper.toDto(any())).thenReturn(new UserAccountDto(userId, "newUser", roles));

        UserAccountDto dto = userAccountService.create(entity);
        assertNotNull(dto);
        verify(passwordEncoder).encode("password");
        verify(userAccountRepository).save(entity);
        verify(userAccountMapper).toDto(entity);
    }

    @Test
    void getById_Success() {
        UUID id = UUID.randomUUID();
        UserAccountEntity entity = new UserAccountEntity();
        entity.setUserId(id);
        entity.setLogin("testUser");
        entity.setEmail("testUser@example.com");

        List<UserRole> roles = Collections.singletonList(UserRole.USER);

        when(userAccountRepository.findById(id)).thenReturn(Optional.of(entity));
        when(userAccountMapper.toDto(any(UserAccountEntity.class)))
                .thenReturn(new UserAccountDto(id, entity.getLogin(), roles));

        UserAccountDto dto = userAccountService.getById(id);

        assertNotNull(dto);
        assertEquals(id, dto.userId());
        verify(userAccountRepository).findById(id);
        verify(userAccountMapper).toDto(entity);
    }

    @Test
    void deleteUser_NotFound() {
        UUID id = UUID.randomUUID();
        when(userAccountRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(BackendException.class, () -> userAccountService.deleteUser(id));
    }

}