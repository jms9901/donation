package com.example.donation.application.controller;

import com.example.donation.annotation.LoginAuth;
import com.example.donation.application.dto.DonationBeneficiaryResponse;
import com.example.donation.application.dto.DonationCommand;
import com.example.donation.application.dto.DonationHistoryResponse;
import com.example.donation.application.dto.DonationRequest;
import com.example.donation.application.service.DonationService;
import com.example.donation.domain.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donation")
public class DonationController {

    private final DonationService donationService;

    @Autowired
    public DonationController(DonationService donationService) {
        this.donationService = donationService;
    }

    @GetMapping("/beneficiaries")
    public ResponseEntity<List<DonationBeneficiaryResponse>> retrieveAllDonationBeneficiary() {
        return ResponseEntity.ok(donationService.retrieveAllDonationBeneficiary());
    }

    // CRUD (Create, Read, Update, Delete)
    // HTTP Method 뭐를 써야할까요? -> POST
    @LoginAuth
    @PostMapping("/api/donation") // 내정보, 얼마줄지, 누구한테
    public ResponseEntity<Void> donation(HttpSession session, @RequestBody DonationRequest request) {
        User loginUser = (User) session.getAttribute("loginUser");
        donationService.donation(new DonationCommand(loginUser, request));
        return ResponseEntity.ok().build();
    }


    @GetMapping("/api/totalAmount/{beneficiaryId}")
    // Read로 사용
    public ResponseEntity<Long> getTotalDonationAmount(@PathVariable Long beneficiaryId) {
        Long totalDonationAmount = donationService.getTotalDonationAmount(beneficiaryId);
        return ResponseEntity.ok(totalDonationAmount);
    }

    /* 아이디 쳐서 주는거
    @GetMapping("/api/donation-history")
    public ResponseEntity<List<DonationHistoryResponse>> retrieveAllDonationHistory(@RequestParam long userId) {
        List<DonationHistoryResponse> resp = donationService.retrieveAllHistory(userId);
        return ResponseEntity.ok(resp);
    }*/


    @LoginAuth
    @GetMapping("/api/donation-history")
    public ResponseEntity<List<DonationHistoryResponse>> retrieveAllDonationHistory(HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        // 만약에 session 의 attribute 에 loginUser 라는 애가 없으면 얘의 반환값은 null 이 나온다.
        // User loginUser = null;
        // loginUser.getId(); // null.getId() -> NullPointerException 발생
        List<DonationHistoryResponse> resp = donationService.retrieveAllHistory(loginUser.getId());
        return ResponseEntity.ok(resp);
    }


    @GetMapping("/api/donation-history/page")
    public ResponseEntity<Page<DonationHistoryResponse>> retrieveAllDonationHistoryWithPage(@RequestParam long userId, @RequestParam(defaultValue = "0") int currentPage) {
        PageRequest pageRequest = PageRequest.of(currentPage, 5);
        Page<DonationHistoryResponse> resp = donationService.retrieveAllHistoryWithPage(userId, pageRequest);
        return ResponseEntity.ok(resp);
    }

    @LoginAuth
    @GetMapping("/api/donation-history-amount")
    public ResponseEntity<Long> retrieveAllDonationAmount(HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        Long resp2 = donationService.retrieveAllHistoryAmount(loginUser.getId());
        return ResponseEntity.ok(resp2);
    }

    // Domain6 장기후원자 개발
    @Operation(summary = "장기 후원자 조회 API")
    @GetMapping("/api/donation-longterm")
    public ResponseEntity<List<String>> getlongtermSupporters() {
        return ResponseEntity.ok(donationService.getlongtermSupporters());
    }

    // Domain7 최다후원자 개발
//    @GetMapping("/api/donation-most")
//    public ResponseEntity<List<DonationHistoryResponse>> getMostSupporters(){
//
//        return ResponseEntity.ok(donationService.getMostSupporters());
//    }

    @GetMapping("/api/donation-most")
    public ResponseEntity<List<String>> getMostSupporters() {

        return ResponseEntity.ok(donationService.getMostSupporters());
    }


}
