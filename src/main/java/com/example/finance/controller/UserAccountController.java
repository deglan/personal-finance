package com.example.finance.controller;

import com.example.finance.aop.annotation.CheckUuid;
import com.example.finance.aop.annotation.ItemWithIdMustExist;
import com.example.finance.auth.TokenService;
import com.example.finance.business.ApiEndpoints;
import com.example.finance.model.dto.UserAccountDto;
import com.example.finance.model.entity.UserAccountEntity;
import com.example.finance.service.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(ApiEndpoints.Endpoints.API + ApiEndpoints.Endpoints.USER)
@AllArgsConstructor
public class UserAccountController {

    private final UserAccountService userAccountService;
    private final TokenService tokenService;

    @PostMapping(value = ApiEndpoints.Endpoints.LOGIN, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<UserAccountDto> login(@RequestParam String login, @RequestParam String password) {
        UserAccountDto byLogin = userAccountService.getByLoginAndPassword(login, password);
        String bearerToken = tokenService.generateBearerToken(byLogin);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, bearerToken)
                .body(byLogin);
    }

    @GetMapping(value = ApiEndpoints.Endpoints.GET_ALL)
    public ResponseEntity<List<UserAccountDto>> getAll() {
        List<UserAccountDto> allUsers = userAccountService.getAll();
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping(value = ApiEndpoints.Endpoints.GET_BY_ID)
    public ResponseEntity<UserAccountDto> getById(@RequestParam UUID id) {
        UserAccountDto userById = userAccountService.getById(id);
        return ResponseEntity.ok(userById);
    }


    @PostMapping(value = ApiEndpoints.Endpoints.CREATE)
    public ResponseEntity<UserAccountDto> create(@RequestBody UserAccountEntity userAccount) {
        UserAccountDto userAccountDto = userAccountService.create(userAccount);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userAccountDto);
    }

    @PutMapping(value = ApiEndpoints.Endpoints.ID)
    @CheckUuid(primaryKey = "userId")
    @ItemWithIdMustExist(serviceClass = UserAccountService.class, checkExistByIdMethodName = "existById")
    public ResponseEntity<UserAccountDto> update(@PathVariable UUID id, @RequestBody UserAccountEntity userAccount) {
        UserAccountDto userAccountDto = userAccountService.updateUser(id, userAccount);
        return ResponseEntity.ok(userAccountDto);
    }

    @DeleteMapping(value = ApiEndpoints.Endpoints.ID)
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        UserAccountDto userAccountDto = userAccountService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
