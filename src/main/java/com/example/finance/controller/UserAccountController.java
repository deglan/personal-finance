package com.example.finance.controller;

import com.example.finance.auth.TokenService;
import com.example.finance.business.ApiEndpoints;
import com.example.finance.model.entity.UserAccountEntity;
import com.example.finance.service.UserAccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiEndpoints.Endpoints.USER)
@AllArgsConstructor
public class UserAccountController {

    private final UserAccountService userAccountService;
    private final TokenService tokenService;

    @PostMapping(value = ApiEndpoints.Endpoints.LOGIN, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<UserAccountEntity> login(@RequestParam String login, @RequestParam String password) {
        UserAccountEntity byLogin = userAccountService.getByLoginAndPassword(login, password);
        String bearerToken = tokenService.generateBearerToken(byLogin);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, bearerToken)
                .body(byLogin);
    }
}
