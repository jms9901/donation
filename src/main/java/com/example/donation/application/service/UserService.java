package com.example.donation.application.service;


import com.example.donation.application.dto.UserRequest;
import com.example.donation.domain.user.entity.User;
import com.example.donation.exception.EmailNullException;
import com.example.donation.exception.EmailOverlapException;
import com.example.donation.exception.ErrorCode;
import com.example.donation.exception.PasswordNullException;
import com.example.donation.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired // 내 콩
    private UserService (UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    // 회원가입하는거 !
    // void가 되면 반환값이 없으니까 반환을 못하는데..
    // 그러면 이제 id를 알려줄 수 없고.. 그러니까 id만 반환하는거니까 정수형중에 대따 큰 long 박기

    public Long register(UserRequest req) {

//
//        String email = req.getEmail();
//        String name = req.getName();
//        String password = req.getPassword();


        User user = new User(null, req.getEmail(), req.getName(), req.getPassword());

        Optional<User> alreadyUser = userRepository.findByEmail(user.getEmail());
        if (alreadyUser.isPresent()) {
            throw new EmailOverlapException("이메일 중복!", ErrorCode.EMAIL_DUPLICATION);
        } else {
            User saveUser = userRepository.save(user);

            return saveUser.getId();
        }
    }

    // 조건문 사용해야......

    public User login(UserRequest req, HttpSession session) {
        User loginUser = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new EmailNullException(ErrorCode.EMAIL_NULL));

        if (!loginUser.getPassword().equals(req.getPassword())) {
            throw new PasswordNullException(ErrorCode.PASSWORD_NULL);
        }

        session.setAttribute("loginUser", loginUser); // 세션에 사용자 객체를 저장하도록 수정
        return loginUser; // 사용자 객체를 반환하도록 수정
    }

}

