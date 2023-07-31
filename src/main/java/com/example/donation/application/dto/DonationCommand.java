package com.example.donation.application.dto;

import com.example.donation.domain.user.entity.User;
import lombok.Data;

@Data
public class DonationCommand {

    private User user;
    private long donationBeneficiaryId;
    private long amount;

    public DonationCommand(User loginUser, DonationRequest request) {
        this.user = loginUser;
        this.donationBeneficiaryId = request.getDonationBeneficiaryId();
        this.amount = request.getAmount();
    }

}
