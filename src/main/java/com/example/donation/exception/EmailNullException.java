package com.example.donation.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmailNullException extends RuntimeException{

    public ErrorCode errorCode;

    public EmailNullException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

}
