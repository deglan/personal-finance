package com.example.finance.listener;

import com.example.finance.model.entity.UserAccountEntity;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PreUpdate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

@Component
public class UserAccountListener {

    @PreUpdate
    public void preUpdate(UserAccountEntity userAccountEntity) {
        if(userAccountEntity.isLoginAction()) {
            userAccountEntity.setLastLogin(LocalDateTime.now());
            userAccountEntity.setLoginAction(false);
        }
    }

    @PostLoad
    public void postLoad(UserAccountEntity userAccount) {
        Period accountAge = Period.between(LocalDate.from(userAccount.getCreatedDate()), LocalDate.now());
        userAccount.setAccountAge(accountAge.toString());
    }
}
