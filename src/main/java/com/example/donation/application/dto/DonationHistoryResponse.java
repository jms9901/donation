package com.example.donation.application.dto;

import com.example.donation.domain.donation.entity.DonationDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonationHistoryResponse {

    private long donationDetailId;
    private String donationBeneficiaryName;
    private long amount;
    private LocalDateTime donationAt;

    public static DonationHistoryResponse from(DonationDetail detail) {
        return new DonationHistoryResponse(
                detail.getId(),
                detail.getDonationBeneficiary().getName(),
                detail.getAmount(),
                detail.getCreatedAt()
        );
    }
}
