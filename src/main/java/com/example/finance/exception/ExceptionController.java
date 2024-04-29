package com.example.finance.exception;

import com.example.finance.exception.model.AuthorizationException;
import com.example.finance.exception.model.FailureOperationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(value = {FailureOperationException.class})
    public ResponseEntity<ProjectError> failureOperationException(FailureOperationException ex) {
        ProjectError failureOperationException = ProjectError.builder()
                .errorName("failureOperationException")
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        log.error(ex.getLocalizedMessage());
        return ResponseEntity.badRequest()
                .body(failureOperationException);
    }

    @ExceptionHandler(value = {AuthorizationException.class})
    public ResponseEntity<ProjectError> authorizationException(AuthorizationException ex) {
        ProjectError authorizationException = ProjectError.builder()
                .errorName("authorizationException")
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(authorizationException);
    }
}