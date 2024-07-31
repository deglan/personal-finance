package com.example.finance.controller;

import com.example.finance.auth.AuthenticationRequest;
import com.example.finance.auth.AuthenticationResponse;
import com.example.finance.auth.AuthenticationService;
import com.example.finance.auth.RegisterRequest;
import com.example.finance.business.ApiEndpoints;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiEndpoints.Endpoints.AUTH)
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping(ApiEndpoints.Endpoints.REGISTER)
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping(ApiEndpoints.Endpoints.LOGIN)
    public ResponseEntity<AuthenticationResponse> authentication(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}
