package com.example.donation.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorResponse {
    private String errorCode;
    private String timestamp;
    private String message;

    public ErrorResponse(ErrorCode errorCode) {
        this.errorCode = errorCode.name();
        this.timestamp = LocalDateTime.now().toString();
        this.message = errorCode.getMessage();
    }

}
