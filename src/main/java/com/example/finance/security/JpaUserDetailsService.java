package com.example.finance.security;

import com.example.finance.exception.model.BackendException;
import com.example.finance.model.entity.UserAccountEntity;
import com.example.finance.model.enums.UserRole;
import com.example.finance.repository.UserAccountRepository;
import com.example.finance.utils.MessageConstants;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class JpaUserDetailsService implements UserDetailsService {

    private final UserAccountRepository userAccountRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Getting data via JPA");

        UserAccountEntity userAccountEntity = userAccountRepository.findByEmail(username)
                .orElseThrow(() -> new BackendException(MessageConstants.USER_NOT_FOUND));

        Optional<UserAccountEntity> byId = userAccountRepository.findById(userAccountEntity.getUserId());

        return new User(userAccountEntity.getUsername(), userAccountEntity.getPassword(),
                userAccountEntity.isEnabled(), userAccountEntity.isAccountNonExpired(), userAccountEntity.isCredentialsNonExpired(),
                userAccountEntity.isAccountNonLocked(), convertToSpringAuthorities(userAccountEntity.getRole()));
    }

    private Collection<? extends GrantedAuthority> convertToSpringAuthorities(Collection<UserRole> roles) {
        if (roles != null && !roles.isEmpty()) {
            return roles.stream()
                    .map(role -> new SimpleGrantedAuthority(role.name()))
                    .collect(Collectors.toSet());
        }
        return List.of();
    }
}
