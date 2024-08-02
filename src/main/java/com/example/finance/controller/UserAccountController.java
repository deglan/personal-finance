package com.example.finance.controller;

import com.example.finance.aop.annotation.CheckUuid;
import com.example.finance.aop.annotation.ItemWithIdMustExist;
import com.example.finance.business.ApiEndpoints;
import com.example.finance.model.dto.UserAccountDto;
import com.example.finance.model.entity.UserAccountEntity;
import com.example.finance.service.UserAccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(ApiEndpoints.Endpoints.API + ApiEndpoints.Endpoints.USER)
@AllArgsConstructor
public class UserAccountController {

    private final UserAccountService userAccountService;

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
