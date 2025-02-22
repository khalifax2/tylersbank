package com.tylersbank.account.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponseDto {
    private String apiPath;
    private HttpStatus errorCode;
    private String message;
    private LocalDateTime dateTime;
}
