package com.example.donation.domain.donation.service;

import com.example.donation.domain.donation.entity.DonationBeneficiary;
import com.example.donation.domain.donation.entity.DonationDetail;
import com.example.donation.domain.user.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DonationDomainServiceTest {

    private DonationDomainService donationDomainService = new DonationDomainService();

    private User user = new User(1L, "slolee@naver.com", "찬준", "123123");
    private DonationBeneficiary beneficiary = new DonationBeneficiary(1L, "기부대상1", "기부대상1");


    @Test
    public void calculateContinuousCount_연속된_기부내역_있을_때_계산_잘_되는지_확인() {
        // given
        List<DonationDetail> details = new ArrayList<>();
        details.add(new DonationDetail(1L, user, beneficiary, 1000, LocalDateTime.of(2023, 7, 1, 0, 0)));
        details.add(new DonationDetail(1L, user, beneficiary, 1000, LocalDateTime.of(2023, 6, 1, 0, 0)));
        details.add(new DonationDetail(1L, user, beneficiary, 1000, LocalDateTime.of(2023, 5, 1, 0, 0)));
        details.add(new DonationDetail(1L, user, beneficiary, 1000, LocalDateTime.of(2023, 4, 1, 0, 0)));
        details.add(new DonationDetail(1L, user, beneficiary, 1000, LocalDateTime.of(2023, 2, 1, 0, 0)));
        details.add(new DonationDetail(1L, user, beneficiary, 1000, LocalDateTime.of(2023, 1, 1, 0, 0)));
        details.add(new DonationDetail(1L, user, beneficiary, 1000, LocalDateTime.of(2022, 12, 1, 0, 0)));
        details.add(new DonationDetail(1L, user, beneficiary, 1000, LocalDateTime.of(2022, 11, 1, 0, 0)));
        details.add(new DonationDetail(1L, user, beneficiary, 1000, LocalDateTime.of(2022, 10, 1, 0, 0)));
        details.add(new DonationDetail(1L, user, beneficiary, 1000, LocalDateTime.of(2022, 9, 1, 0, 0)));

        // when
        int count = donationDomainService.calculateContinuousCount(details);

        // then
        assertEquals(count, 4);
    }

    @Test
    public void calculateContinuousCount_이전_년도_까지_연속으로_계산_잘_되는지_확인() {
        // given
        List<DonationDetail> details = new ArrayList<>();
        details.add(new DonationDetail(1L, user, beneficiary, 1000, LocalDateTime.of(2023, 7, 1, 0, 0)));
        details.add(new DonationDetail(1L, user, beneficiary, 1000, LocalDateTime.of(2023, 6, 1, 0, 0)));
        details.add(new DonationDetail(1L, user, beneficiary, 1000, LocalDateTime.of(2023, 5, 1, 0, 0)));
        details.add(new DonationDetail(1L, user, beneficiary, 1000, LocalDateTime.of(2023, 4, 1, 0, 0)));
        details.add(new DonationDetail(1L, user, beneficiary, 1000, LocalDateTime.of(2023, 3, 1, 0, 0)));
        details.add(new DonationDetail(1L, user, beneficiary, 1000, LocalDateTime.of(2023, 2, 1, 0, 0)));
        details.add(new DonationDetail(1L, user, beneficiary, 1000, LocalDateTime.of(2023, 1, 1, 0, 0)));
        details.add(new DonationDetail(1L, user, beneficiary, 1000, LocalDateTime.of(2022, 12, 1, 0, 0)));
        details.add(new DonationDetail(1L, user, beneficiary, 1000, LocalDateTime.of(2022, 10, 1, 0, 0)));
        details.add(new DonationDetail(1L, user, beneficiary, 1000, LocalDateTime.of(2022, 9, 1, 0, 0)));

        // when
        int count = donationDomainService.calculateContinuousCount(details);

        // then
        assertEquals(count, 8);
    }


    @Test
    public void calculateContinuousCount_직전_달에_기부_안했으면_0_반환되는지_확인() {
        // given
        List<DonationDetail> details = new ArrayList<>();
        details.add(new DonationDetail(1L, user, beneficiary, 1000, LocalDateTime.of(2023, 6, 1, 0, 0)));
        details.add(new DonationDetail(1L, user, beneficiary, 1000, LocalDateTime.of(2023, 5, 1, 0, 0)));
        details.add(new DonationDetail(1L, user, beneficiary, 1000, LocalDateTime.of(2023, 4, 1, 0, 0)));

        // when
        int count = donationDomainService.calculateContinuousCount(details);

        // then
        assertEquals(count, 0);
    }

    @Test
    public void calculateContinuousCount_기부내역이_없을때_0_반환되는지_확인() {
        // given
        List<DonationDetail> details = Collections.emptyList();

        // when
        int count = donationDomainService.calculateContinuousCount(details);

        // then
        assertEquals(count, 0);
    }

}
