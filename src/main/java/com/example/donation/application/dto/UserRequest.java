package com.example.donation.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString //toString() 메소드 자동 생성 요놈이 있어야 문자열을 쉽게 볼 수 있음. 안그러면 못 봐 @fa2fmosdaf <- 이난리남
public class UserRequest {

    private final String email;
    private final String password;
    private final String name;

}
