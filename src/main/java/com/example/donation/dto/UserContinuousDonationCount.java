package com.example.donation.dto;

import com.example.donation.domain.user.entity.User;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserContinuousDonationCount implements Comparable {

    private User user;
    private int continuousDonationCount;

    @Override
    public int compareTo(Object o) {
        // 정렬할 기준에 대해서 적어주면 된다.
        // 결과가 음수면 내림차순 정렬.
        // 결과가 양수면 오름차순 정렬.
        // 결과가 0이면 똑같다.
        UserContinuousDonationCount other = (UserContinuousDonationCount) o;
        // TODO: 이 Count 순으로 잘 정렬되는지 확인해주길 바람
        //       오름차순으로 정렬이 되는지, 내림차순으로 정렬이 되는지 확인
        return other.continuousDonationCount - this.continuousDonationCount;
    }

    public String getUserName() {
        return this.user.getName();
    }

}
