package com.example.donation.application.interceptor;

import com.example.donation.annotation.LoginAuth;
import com.example.donation.domain.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SessionCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        if (checkAnnotation(handler)) {
            HttpSession session = req.getSession();
            User loginUser = (User) session.getAttribute("loginUser");
            if (loginUser == null) {
                throw new RuntimeException("로그인한 사용자만 호출 가능합니다!"); // TODO : CustomException 변경 필요
            }
        }
        return true;
    }

    public boolean checkAnnotation(Object handler) {
        if (handler instanceof HandlerMethod method) {
            return method.getMethodAnnotation(LoginAuth.class) != null;
        }
        return false;
    }

}
