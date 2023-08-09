package com.example.donation.application.controller;

import com.example.donation.application.dto.UserRequest;
import com.example.donation.domain.user.entity.User;
import com.example.donation.annotation.LoginAuth;
import com.example.donation.application.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired // DI 콩콩콩콩
    public UserController(UserService userService){
        this.userService = userService;
    }

    /**
     *
     public String ping(){
     return "pong!!!";
     }*/

    @Operation(summary = "회원가입 API")
    @PostMapping("/api/join")
    public Long register(@RequestBody UserRequest req){

        Long createdUserId = userService.register(req);

        return createdUserId;

    }

    @Operation(summary = "로그인 API")
    @PostMapping("/api/login")
    public User login (@RequestBody UserRequest req , HttpSession session) {

        User  loginID = userService.login(req , session);

        return loginID;
    }

    @GetMapping("/api/Hello")
    public String hello(){
        return "Hello!!";
    }

    @Operation(summary = "로그인 한 사용자의 메인화면 API")
    @LoginAuth
    @GetMapping("/api/main")
    public String main(){
        return "환영합니다";
    }

}