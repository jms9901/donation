package com.example.donation.domain.donation.repository;

import com.example.donation.domain.donation.entity.DonationBeneficiary;
import com.example.donation.domain.donation.entity.DonationDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationDetailRepository extends CrudRepository<DonationDetail, Long> {
    @Query("SELECT db FROM DonationDetail db WHERE db.donationBeneficiary.id = :donationBeneficiaryId")
    List<DonationDetail> findAllByDonationBeneficiaryId(@Param("donationBeneficiaryId") Long donationBeneficiaryId);

    @Query("SELECT db FROM DonationDetail db WHERE db.user.id = :userId")
    // @Query(nativeQuery = true, value = "SELECT * FROM donation_detail WHERE user_id = ?")
    List<DonationDetail> findAllByUserId2(@Param("userId") long userId);

    Page<DonationDetail> findAllByUserId(long userId, PageRequest pageRequest);
}