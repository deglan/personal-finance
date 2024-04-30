package com.example.finance.service;

import com.example.finance.exception.model.AuthorizationException;
import com.example.finance.exception.model.FailureOperationException;
import com.example.finance.model.entity.UserAccountEntity;
import com.example.finance.repository.UserAccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserAccountService {

    protected static final String MESSAGE = "There is no such user";
    private final UserAccountRepository userAccountRepository;

    private final PasswordEncoder passwordEncoder;

    public UserAccountEntity getByLoginAndPassword(String login, String password) {
        return userAccountRepository.findByLoginAndActiveTrueAndDeletedFalse(login)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .orElseThrow(() -> new AuthorizationException(MESSAGE));
    }

    public UserAccountEntity getByLogin(String login) {
        return userAccountRepository.findByLoginAndActiveTrueAndDeletedFalse(login)
                .orElseThrow(() -> new FailureOperationException(MESSAGE));
    }
}
