package com.example.finance.exception.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BackendException extends RuntimeException {

    public BackendException(String message) {
        super(message);
    }
}
