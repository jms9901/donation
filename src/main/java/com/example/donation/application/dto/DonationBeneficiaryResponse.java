package com.example.donation.application.dto;

import com.example.donation.domain.donation.entity.DonationBeneficiary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonationBeneficiaryResponse {

    private long donationBeneficiaryId;
    private String name;
    private String contents;

    public static DonationBeneficiaryResponse from(DonationBeneficiary donationBeneficiary) {
        return new DonationBeneficiaryResponse(
                donationBeneficiary.getId(),
                donationBeneficiary.getName(),
                donationBeneficiary.getContents()
        );
    }
}
