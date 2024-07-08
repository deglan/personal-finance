package com.example.finance.exception;

import com.example.finance.exception.model.AuthorizationException;
import com.example.finance.exception.model.BackendException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(value = {BackendException.class})
    public ResponseEntity<ProjectError> failureOperationException(BackendException ex) {
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

    @ExceptionHandler(value = {ObjectOptimisticLockingFailureException.class})
    public ResponseEntity<ProjectError> handleOptimisticLockingFailure(ObjectOptimisticLockingFailureException ex) {
        ProjectError lockingFailureException = ProjectError.builder()
                .errorName("lockingFailureException")
                .message("Resource is locked. Please try again later.")
                .timestamp(LocalDateTime.now())
                .build();
        log.error(ex.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.LOCKED)
                .body(lockingFailureException);
    }
}