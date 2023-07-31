package com.example.donation.application.interceptor;


import com.example.donation.domain.user.entity.User;
import com.example.donation.annotation.LoginAuth;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


       /* HandlerMethod parsed = (HandlerMethod) handler;

        if (handler instanceof HandlerMethod) {
            HandlerMethod parsed = (HandlerMethod) handler;
            해당 핸들러에 해당 어노테이션이 붙어있으면 어노테이션 정보를 가져와요
            만약 안붙어있으면 NULL 값을 반환합니다. -> 해당 인터셉터 로직을 수행하면 안되죠. -> 바로 리턴 true 해야한다.
            System.out.println(((HandlerMethod) handler).getMethodAnnotation(LoginAuth.class));

        }*/


        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            LoginAuth loginAuth = handlerMethod.getMethodAnnotation(LoginAuth.class);



            // LoginAuth 어노테이션이 없는 경우에는 인증하지 않기!
            if (loginAuth != null) {

                // SESSION에서 JSESSIONID를 가져온다..
                HttpSession session = request.getSession();


                User sessionId = (User) session.getAttribute("loginUser");
                    // LoginUser



                // JSESSIONID가 없으면 인증 실패
                if (sessionId == null) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
                    return false;
                }

                // JSESSIONID가 있으면 인증 성공
                return true;
            }

            // 어노테이션 구분여부 확인해보기..!
            System.out.println("어노테이션 있찌");
        }



        return true;
    }
}
