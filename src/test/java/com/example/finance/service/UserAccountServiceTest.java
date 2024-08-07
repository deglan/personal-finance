package com.example.finance.service;

import com.example.finance.exception.model.BackendException;
import com.example.finance.factory.UserMockFactory;
import com.example.finance.mapper.UserAccountMapper;
import com.example.finance.model.dto.UserAccountDto;
import com.example.finance.model.entity.UserAccountEntity;
import com.example.finance.repository.UserAccountRepository;
import com.example.finance.service.cache.UserServiceCacheManager;
import com.example.finance.utils.TestConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
    @Mock
    private UserServiceCacheManager userServiceCacheManager;

    @InjectMocks
    private UserAccountService userAccountService;

    @Test
    void getByLoginAndPassword_loginProcedure_Success() {
        //GIVEN
        UserAccountEntity entity = UserMockFactory.createUserEntity();
        when(userAccountRepository.findByLoginAndActiveTrueAndDeletedFalse(TestConstants.USER_LOGIN))
                .thenReturn(Optional.of(entity));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(userAccountMapper.toDto(any())).thenReturn(UserMockFactory.createUserDto());

        //WHEN THEN
        assertDoesNotThrow(() -> userAccountService.getByLoginAndPassword(TestConstants.USER_LOGIN, TestConstants.USER_PASSWORD));
    }

    @Test
    void create_createUser_Success() {
        //GIVEN
        UserAccountEntity entity = UserMockFactory.createUserEntity();
        when(passwordEncoder.encode(TestConstants.USER_PASSWORD)).thenReturn(TestConstants.USER_ENCODED_PASSWORD);
        when(userAccountRepository.save(any(UserAccountEntity.class))).thenReturn(entity);
        when(userAccountMapper.toDto(any())).thenReturn(UserMockFactory.createUserDto());

        //WHEN
        UserAccountDto dto = userAccountService.create(entity);

        //THEN
        assertNotNull(dto);
        verify(passwordEncoder).encode(TestConstants.USER_PASSWORD);
        verify(userAccountRepository).save(entity);
        verify(userAccountMapper).toDto(entity);
    }

    @Test
    void getById_findUserById_Success() {
        // GIVEN
        UserAccountEntity entity = UserMockFactory.createUserEntity();
        Mockito.lenient().when(userServiceCacheManager.findById(TestConstants.USER_UUID)).thenReturn(Optional.of(entity));
        when(userAccountMapper.toDto(any(UserAccountEntity.class)))
                .thenReturn(UserMockFactory.createUserDto());
        // WHEN
        UserAccountDto dto = userAccountService.getById(TestConstants.USER_UUID);

        //THEN
        assertNotNull(dto);
        assertEquals(TestConstants.USER_UUID, dto.userId());
        verify(userAccountMapper).toDto(entity);
    }

    @Test
    void deleteUser_deleteUserById_NotFound() {
        //GIVEN WHEN
        when(userAccountRepository.findById(TestConstants.USER_UUID)).thenReturn(Optional.empty());

        //THEN
        assertThrows(BackendException.class, () -> userAccountService.deleteUser(TestConstants.USER_UUID));
    }

}