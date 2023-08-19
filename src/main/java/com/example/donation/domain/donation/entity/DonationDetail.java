package com.example.donation.domain.donation.entity;

import com.example.donation.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "donation_detail")
public class DonationDetail {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "donation_detail_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "donation_beneficiary_id", referencedColumnName = "donation_beneficiary_id")
    private DonationBeneficiary donationBeneficiary;

    private long amount;

    private LocalDateTime createdAt = LocalDateTime.now();

}
