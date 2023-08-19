package com.example.donation.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmailOverlapException extends RuntimeException {

    public ErrorCode errorCode;

    /*   private ErrorCode errorCode;
       private String message;

       @Override
       public String toString() {
           if (message == null) {
               return errorCode.getMessage();
           }
           return String.format("%s. %s",errorCode.getMessage(), message);
       } */
    public EmailOverlapException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
