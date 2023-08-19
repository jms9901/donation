package com.example.donation.domain.donation.repository;

import com.example.donation.domain.donation.entity.DonationBeneficiary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationBeneficiaryRepository extends CrudRepository<DonationBeneficiary, Long> {
}
