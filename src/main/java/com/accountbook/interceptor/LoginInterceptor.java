package com.accountbook.interceptor;

import com.accountbook.common.utils.CookieUtils;
import com.accountbook.domain.entity.User;
import com.accountbook.domain.repository.user.UserRepository;
import com.accountbook.dto.user.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

/**
 * LoginInterceptor
 *
 * @author donggun
 * @since 2022/03/08
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public boolean preHandle (HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 1. UID 쿠키 조회
        Cookie cookie = CookieUtils.getCookieByName(request.getCookies(), "UID");

        if(cookie != null) {
            String uid = cookie.getValue();
            User user = userRepository.findByUid(uid);

            // 3. expireDate
            LocalDateTime now = LocalDateTime.now();
            if(now.isBefore(user.getExpireDate())) {

                // 4. Session 생성
                HttpSession session = request.getSession();
                session.setAttribute("loginInfo", new UserDto(user));
                session.setMaxInactiveInterval(60 * 30);

                // 5. cookie 만료 기간 갱신 및 DB 업데이트
                cookie.setMaxAge(60 * 60 * 24 * 14);
                userRepository.updateExpireDateByUid(uid, now.plusDays(14));

                // 6. 사용자가 이동하려고 했던 페이지로 리다이렉트
                String requestURI = request.getRequestURI();
                //response.sendRedirect(requestURI);
                return true;
            }

            // 기한 만료
            else {
                log.info("Login expireDate is over...");
                //response.sendRedirect("/login");
                return false;
            }
        }
        // uid 없는 경우
        else {
            log.info("No cookie with name [UID]...");
            //response.sendRedirect("/login");
            return false;
        }

    }
}
