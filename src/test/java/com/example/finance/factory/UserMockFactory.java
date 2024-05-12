package com.example.finance.factory;

import com.example.finance.model.dto.UserAccountDto;
import com.example.finance.model.entity.UserAccountEntity;
import com.example.finance.model.enums.UserRole;
import com.example.finance.utils.TestConstants;
import lombok.experimental.UtilityClass;

import java.util.Collections;

@UtilityClass
public class UserMockFactory {

    public UserAccountEntity createUserEntity() {
        UserAccountEntity user = new UserAccountEntity();
        user.setUserId(TestConstants.USER_UUID);
        user.setLogin(TestConstants.USER_LOGIN);
        user.setPassword(TestConstants.USER_PASSWORD);
        user.setEmail(TestConstants.USER_EMAIL);
        user.setRole(Collections.singletonList(UserRole.USER));
        return user;
    }

    public UserAccountDto createUserDto() {
        return new UserAccountDto(
                TestConstants.USER_UUID,
                TestConstants.USER_LOGIN,
                Collections.singletonList(UserRole.USER)
        );
    }
}
