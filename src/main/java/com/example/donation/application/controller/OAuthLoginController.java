package com.example.donation.application.controller;

import com.example.donation.client.oauth.OauthKakaoClient;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class OAuthLoginController {

    private final OauthKakaoClient oauthKakaoClient;

    @GetMapping("/oauth2/kakao/login")
    public void oauthLogin(
            HttpServletResponse response
    ) throws IOException {
        String redirectionUrl = oauthKakaoClient.generateRedirectionUrl();
        response.sendRedirect(redirectionUrl);
    }

    @GetMapping("/oauth2/kakao/callback")
    public void oauthCallback(@RequestParam String code){
        System.out.println("Code 정상적으로 넘어옴~");
        System.out.println(code);
    }
}
