package com.example.finance.utils;

import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class TestConstants {

    public UUID USER_UUID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
    public String USER_LOGIN = "user1";
    public String USER_PASSWORD = "password1";
    public String USER_ENCODED_PASSWORD = "password2";
    public String USER_EMAIL = "testUser@example.com";
}
