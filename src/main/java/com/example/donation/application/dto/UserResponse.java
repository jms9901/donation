package com.example.donation.application.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor // 생성자
public class UserResponse {

    private Long id;
    private String email;
    private String name;
    private String password;

}
