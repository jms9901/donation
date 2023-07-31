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
@Table(name = "uuser")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //DB테이블 컬럼과 1:1 매칭
    @Column(name= "user_id")
    private Long id;

    @Column(unique = true)
    private String email;
    private String name;
    private String password;



}