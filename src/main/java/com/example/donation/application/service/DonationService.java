package com.example.donation.application.service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.example.donation.application.dto.DonationHistoryResponse;
import com.example.donation.domain.user.entity.User;
import com.example.donation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.donation.application.dto.DonationBeneficiaryResponse;
import com.example.donation.application.dto.DonationCommand;
import com.example.donation.domain.donation.entity.DonationBeneficiary;
import com.example.donation.domain.donation.entity.DonationDetail;
import com.example.donation.domain.donation.repository.DonationBeneficiaryRepository;
import com.example.donation.domain.donation.repository.DonationDetailRepository;


@Service
public class DonationService {
    private final DonationBeneficiaryRepository donationBeneficiaryRepository;
    private final DonationDetailRepository donationDetailRepository;
    private final UserRepository userRepository;


    @Autowired
    public DonationService(DonationBeneficiaryRepository donationBeneficiaryRepository, DonationDetailRepository donationDetailRepository,
                           UserRepository userRepository) {
        this.donationBeneficiaryRepository = donationBeneficiaryRepository;
        this.donationDetailRepository = donationDetailRepository;
        this.userRepository = userRepository;
    }

    public List<DonationBeneficiaryResponse> retrieveAllDonationBeneficiary() {
        // 1. 데이터베이스에서 기부 대상자들 다 조회해야겠다.
        List<DonationBeneficiary> donationBeneficiaries = (List<DonationBeneficiary>) donationBeneficiaryRepository.findAll();
        // 2. 그 조회된 기부 대상자들을 응답객체(DTO) 로 만들어서 반환해야겠다.
        /*List<DonationBeneficiaryResponse> response = new ArrayList<>();
        for (DonationBeneficiary donationBeneficiary : donationBeneficiaries) {
            response.add(DonationBeneficiaryResponse.from(donationBeneficiary));
        }*/
        List<DonationBeneficiaryResponse> response = donationBeneficiaries.stream()
                .map(DonationBeneficiaryResponse::from)
                .collect(Collectors.toList());
        return response;
    }

    @Transactional
    public void donation(DonationCommand command) {
        // 1. 사용자로부터 받은 donationBeneficiaryId 로 DonationBeneficiary Entity 조회
        DonationBeneficiary donationBeneficiary = donationBeneficiaryRepository.findById(command.getDonationBeneficiaryId())
                .orElseThrow();

        // 2. DonationCommand -> DonationDetail Entity 로 변경
        DonationDetail donationDetail = DonationDetail.builder()
                .user(command.getUser())
                .donationBeneficiary(donationBeneficiary)
                .amount(command.getAmount())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // 3. DonationDetail 를 DB 에 저장
        donationDetailRepository.save(donationDetail);
    }


    public Long getTotalDonationAmount(Long donationBeneficiaryId) {
        // 2. 해당 기부 수혜자에 대한 모든 기부 내역을 가져오기
        List<DonationDetail> donationDetails = donationDetailRepository.findAllByDonationBeneficiaryId(donationBeneficiaryId);

        // 3. 기부 내역들의 총액 계산하기
        // TODO :
        //    1. DonationDetail -> Long (amount)   : map
        //    2. 마무리연산이 collect 가 아니라 sum()
        /*Long totalDonationAmount = 0L;
        for (DonationDetail donationDetail : donationDetails) {
            totalDonationAmount += donationDetail.getAmount();
        }*/

        // Stream으로 수정

        // 4. ResponseEntity로 총액 반환
        return donationDetails.stream()
                .mapToLong(DonationDetail::getAmount)
                .sum();
    }

    //  Stream 예시
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        Stream<Integer> stream = list.stream();

        List<String> result = stream.filter((integer) -> integer % 2 == 0)
                .map((str) -> {
                    System.out.println(str);
                    return "추가로 붙은 헤더, " + str;
                })
                .collect(Collectors.toList());

        List<String> strList = new ArrayList<>();
        strList.add("A,B,C,D");
        strList.add("a,b,c,d");
        strList.add("박,찬,준");
        Stream<String> stream1 = strList.stream();

        List<String> collect = stream1.flatMap(str -> {
            return Arrays.stream(str.split(","));
        }).collect(Collectors.toList());
    }

    public List<DonationHistoryResponse> retrieveAllHistory(long userId) {
        // 1. 기부내역에서 user_id 와 사용자가 일치하는 것들을 모두 찾는다.
        List<DonationDetail> histories = donationDetailRepository.findAllByUserId2(userId);
        // 2. 찾은 기부내역을 리스트 형태로 반환한다.
        return histories.stream()
                .map(DonationHistoryResponse::from)
                .collect(Collectors.toList());
    }

    public Page<DonationHistoryResponse> retrieveAllHistoryWithPage(long userId, PageRequest pageRequest) {
        // 1. 기부내역에서 user_id 와 사용자가 일치하는 것들을 모두 찾는다.
        Page<DonationDetail> histories = donationDetailRepository.findAllByUserId(userId, pageRequest);
        // 2. 찾은 기부내역을 리스트 형태로 반환한다.
        return histories.map(DonationHistoryResponse::from);
    }

    public Long retrieveAllHistoryAmount(long userId) {
        // 1. 기부내역에서 user_id와 사용자가 일치하는 것들 중 Amount만 찾아온다.
        List<DonationDetail> totalAmount = donationDetailRepository.findAllByUserId2(userId);
        // 2. 찾은 Amount의 총 금액을 계산하여 반환한다.
        return totalAmount.stream()
                .mapToLong(DonationDetail::getAmount)
                .sum();

    }

    // 6. 장기후원자 조회

    public List<String> getlongtermSupporters() {
        // 1. 모든 User 정보 조회한다 -> UserRepository한테 요청
        List<User> users = userRepository.findAll();

       // 2. 각 User에 대한 모든 DonationDetail을 조회한다. -> DonationDetailRepesitory한테 요청
        Map<User, List<DonationDetail>> userDonationDetails = new HashMap<>();
        for (User user : users) {
            List<DonationDetail> dusthrDonationDetails = donationDetailRepository.findAllByUserId2(user.getId());
            userDonationDetails.put(user, dusthrDonationDetails);
        }

       // 3. 각 User의 연속기부횟수를 계산한다. -> Domain에게 맡기기
        Map<User, Integer> userDonationCountMap = new HashMap<>();
        for (Map.Entry<User, List<DonationDetail>> e : userDonationDetails.entrySet()){
            User user = e.getKey();
            List<DonationDetail> userCountDonationDetails = e.getValue();
            long totalCount = userCountDonationDetails.stream()
                    .mapToLong(DonationDetail::getId)
                    .count();
            userDonationCountMap.put(user, (int)totalCount);
        }

        // 4. Map<User, 연속기부횟수> 에서 연속기부횟수가 가장 큰 3명을 뽑아 반환한다.
            return userDonationCountMap.entrySet().stream()
                    .sorted(Map.Entry.<User, Integer>comparingByValue().reversed())
                    .limit(3)
                    .map(entry -> entry.getKey().getName())
                    .collect(Collectors.toList());

   }

    // 7. 최다후원자 찾기  (돈)
//    public List<DonationHistoryResponse> getMostSupporters() {
//
//        // 1.모든 Donation Detail을 조회한다. -> DonationDetailRepository 요청 -> List<DonationDetail>
//        List<DonationDetail> donationDetails = (List<DonationDetail>) donationDetailRepository.findAll();
//
//        // 2. 조회된 List<DonationDetail>을 User로 Groupping한다 (Stream의 Grouping 기능 사용)
//        //    -> Map<user, List<DonationDetail>> 반환
//        Map<User, List<DonationDetail>> groupedDonationDetails = donationDetails.stream()
//                .collect(Collectors.groupingBy(DonationDetail::getUser));
//
//        // 3. Groupping된 Map<User, List<DonationDetail>>을 순환하며
//        //    각 User의 List<DonationDetail>의 amount를 더한다 -> Map<User,Long>
//        Map<User, Long> userDonationTotalMap = new HashMap<>();
//        for (Map.Entry<User, List<DonationDetail>> entry : groupedDonationDetails.entrySet()) {
//            User user = entry.getKey();
//            List<DonationDetail> userDonationDetails = entry.getValue();
//            long totalAmount = userDonationDetails.stream()
//                    .mapToLong(DonationDetail::getAmount)
//                    .sum();
//            userDonationTotalMap.put(user, totalAmount);
//        }
//        // 4. Map<User, Long>에서 Long이 가장 큰 3개의 User를 찾아서 반환한다.
//
//        return userDonationTotalMap.entrySet().stream()
//                .sorted(Map.Entry.<User, Long>comparingByValue().reversed())
//                .limit(3)
//                .map(entry -> {
//                    DonationHistoryResponse response = new DonationHistoryResponse();
//                    response.setAmount(entry.getValue());
//                    response.setDonationBeneficiaryName(entry.getKey().getName());
//                    // 기부 상세 정보로 DonationHistoryResponse 객체를 생성
//                    return response;
//                })
//                .collect(Collectors.toList());
//
//    }


    public List<String> getMostSupporters() {
        // 1. 모든 기부 상세 정보를 조회합니다. -> DonationDetailRepository 요청 -> List<DonationDetail>
        List<DonationDetail> donationDetails = (List<DonationDetail>) donationDetailRepository.findAll();

        // 2. 조회된 List<DonationDetail>을 사용자(User)로 그룹화합니다. (스트림의 그룹화 기능 사용)
        //    -> Map<User, List<DonationDetail>> 반환
        Map<User, List<DonationDetail>> groupedDonationDetails = donationDetails.stream()
                .collect(Collectors.groupingBy(DonationDetail::getUser));

        // 3. 그룹화된 Map<User, List<DonationDetail>>을 순환하며
        //    각 사용자의 기부 목록의 총 금액을 계산합니다 -> Map<User, Long>
        Map<User, Long> userDonationTotalMap = new HashMap<>();
        for (Map.Entry<User, List<DonationDetail>> e : groupedDonationDetails.entrySet()) {
            User user = e.getKey();
            List<DonationDetail> userDonationDetails = e.getValue();
            long totalAmount = userDonationDetails.stream()
                    .mapToLong(DonationDetail::getAmount)
                    .sum();
            userDonationTotalMap.put(user, totalAmount);
        }

        // 4. Map<User, Long>에서 값이 가장 큰 3개의 사용자 이름을 추출하여 리스트로 반환합니다.

        return userDonationTotalMap.entrySet().stream()
                .sorted(Map.Entry.<User, Long>comparingByValue().reversed())
                .limit(3)
                .map(entry -> entry.getKey().getName())
                .collect(Collectors.toList());
    }
}
