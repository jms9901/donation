package com.example.donation.domain.user.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_with_oauth")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //DB테이블 컬럼과 1:1 매칭
    @Column(name= "user_id")
    private Long id;

    private String email;
    private String name;
    private String password;

    @Column(name = "oauth_login_yn")
    private boolean isOAuthLogin;

    private String providerName;
    private long providerId;

    public static User forGeneral(String email, String name, String password) {
        return new User(null, email, name, password, false, null, 0);
    }

    public static User forOAuth(String providerName, long providerId, String nickname) {
        return new User(null, null, nickname, null, true, providerName, providerId);
    }


}