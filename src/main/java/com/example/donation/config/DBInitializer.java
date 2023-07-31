package com.example.donation.config;

import java.util.List;

import com.example.donation.domain.donation.entity.DonationBeneficiary;
import com.example.donation.domain.donation.repository.DonationDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;

import com.example.donation.domain.donation.repository.DonationBeneficiaryRepository;
import org.springframework.boot.ApplicationRunner;

public class DBInitializer implements ApplicationRunner {

    // Autowired -> DI -> 의존성 주입받는다.
    // 의존성 주입방식은 3가지가 있죠 -> 필드 주입방식
    // 기본적으로 특별한 이슈가 없으면 생성자 주입방식을 사용하는게 좋다.
    private final DonationBeneficiaryRepository donationBeneficiaryRepository;

    @Autowired
    private DBInitializer(DonationBeneficiaryRepository donationBeneficiaryRepository) {
        this.donationBeneficiaryRepository = donationBeneficiaryRepository;
    }

    // DBMS -> Oracle, MySQL, MongoDB, PostgreSQL, H2 ...
    @Override
    public void run(ApplicationArguments args) throws Exception {
        // TODO : DB 기부대상 데이터 Insert
        // Repository 를 이용해서 DB 에 데이터를 넣는다.
        // Repository 는 번역가
        // 번역가한테 데이터좀 넣어줘. 라고 하면서 데이터를 쥐어줘야죠.
        // Repository 가 받을 수 있는 데이터의 형태는? -> JPA Entity

        DonationBeneficiary beneficiary1 = new DonationBeneficiary("기부대상1", "기부내용1");
        DonationBeneficiary beneficiary2 = new DonationBeneficiary("기부대상2", "기부내용2");
        DonationBeneficiary beneficiary3 = new DonationBeneficiary("기부대상3", "기부내용3");
        donationBeneficiaryRepository.saveAll(List.of(beneficiary1, beneficiary2, beneficiary3));
    }

}
