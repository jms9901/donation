package com.example.donation.service;

import com.example.donation.application.service.UserService;
import com.example.donation.application.dto.UserRequest;
import com.example.donation.domain.user.entity.User;
import com.example.donation.exception.EmailNullException;
import com.example.donation.exception.EmailOverlapException;
import com.example.donation.exception.PasswordNullException;
import com.example.donation.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void login_이메일로_검색이_안될때_에러_확인() {
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        UserRequest req = new UserRequest("jms9901@naver.com", "1234a", "문선");
        HttpSession session = mock(HttpSession.class);

        assertThrows(EmailNullException.class, () -> userService.login(req, session));
    }

    @Test
    public void login_찾은_사용자의_패스워드가_틀리면_에러_확인() {
        // given
        User user = new User(1L, "jms9901@naver.com", "문선", "1234a");
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        UserRequest req = new UserRequest("jms9901@naver.com", "167234a", "문선");
        HttpSession session = mock(HttpSession.class);

        // when & then
        assertThrows(PasswordNullException.class, () -> userService.login(req, session));
    }


    @Test
    public void login_성공_케이스_확인() {
        // given
        User user = new User(1L, "jms9901@naver.com", "문선", "1234a");
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        UserRequest req = new UserRequest("jms9901@naver.com", "1234a", "문선");
        HttpSession session = mock(HttpSession.class); // HttpSession 모킹

        // when
        User loginUser = userService.login(req, session);

        // then
        assertEquals(loginUser.getEmail(), req.getEmail());
        assertEquals(loginUser.getName(), "문선");
    }


    @Test
    public void register_가입하려는아이디가_이미있는_아이디면_에러_확인() {
        // given
        User user = new User(1L, "jms9901@naver.com", "문선", "1234a");
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        UserRequest req = new UserRequest("jms9901@naver.com", "167234a", "문선");

        // when & then
        assertThrows(EmailOverlapException.class, () -> userService.register(req));
    }

    @Test
    public void register_성공_케이스_확인() {

        // given
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        UserRequest req = new UserRequest("jms9901@naver.com", "167234a", "문선");
        when(userRepository.save(any())).thenReturn(new User(1L, "jms9901@naver.com", "167234a", "문선"));

        // when
        // User 레포지토리에 email 정보가 없다면
        Long id = userService.register(req);

        // then
        // 가입했습니다 띄우기
        assertEquals(id, 1L);
    }

}