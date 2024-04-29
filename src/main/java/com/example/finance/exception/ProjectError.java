package com.example.finance.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectError {

    private LocalDateTime timestamp;
    private String message;
    private Integer errorCode;
    private String errorName;
    private String path;
}
