package com.example.donation.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /*409 중복된 데이터 존재 */
    EMAIL_DUPLICATION(409, "아이디가 이미 존재합니다!"),
    EMAIL_NULL(409, "해당 아이디는 존재하지 않습니다!"),
    PASSWORD_NULL(409,"비밀번호가 틀렸습니다.")
    ;

    private final int status;
    private final String message;
}
