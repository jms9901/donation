package com.example.donation.client.oauth;

import org.springframework.stereotype.Component;

@Component
public class OauthKakaoClient {

    private final static String KAKAO_OAUTH_BASE_URL ="https://kauth.kakao.com";
    private final String clientId = "46db91d20f51f4f7ec2c8973396f2564";
    private final String redirectUri = "http://localhost:8084/oauth2/kakao/callback";

    public String generateRedirectionUrl(){
        return KAKAO_OAUTH_BASE_URL + "/oauth/authorize"
                + "?client_id=" + clientId
                + "&redirect_uri=" + redirectUri
                + "&response_type=" + "code";
    }
}
