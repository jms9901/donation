package com.example.donation.domain.donation.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "DonationBeneficiary")
public class DonationBeneficiary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //DB테이블 컬럼과 1:1 매칭
    @Column(name = "donation_beneficiary_id")
    private Long id;

    private String name;
    private String contents;


    public DonationBeneficiary(String name, String contents) {
        this.name = name;
        this.contents = contents;
    }
}