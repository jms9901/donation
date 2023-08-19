package com.example.donation.domain.donation.service;

import com.example.donation.domain.donation.entity.DonationDetail;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DonationDomainService {

    public int calculateContinuousCount(List<DonationDetail> donationDetails) {
        List<LocalDate> donationDates = donationDetails.stream()
                .map(donationDetail -> {
                    var donationDate = donationDetail.getCreatedAt();
                    return LocalDate.of(donationDate.getYear(), donationDate.getMonth(), 1);
                }).distinct().toList();

        LocalDate start = LocalDate.now().withDayOfMonth(1).minusMonths(1);
        int count = 0;
        while (donationDates.contains(start)) {
            count += 1;
            start = start.minusMonths(1);
        }
        return count;
    }

}
