package com.accountbook.filter;

import com.accountbook.common.utils.CookieUtils;
import com.accountbook.domain.entity.User;
import com.accountbook.domain.repository.user.UserRepository;
import com.accountbook.dto.user.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * LoginFilter
 *
 * @author donggun
 * @since 2022/03/08
 */
@Slf4j
public class LoginFilter implements Filter {

    @Autowired
    UserRepository userRepository;

    @Override
    public void init (FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy () {
        Filter.super.destroy();
    }

    @Override
    @Transactional
    public void doFilter (ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        log.info("LoginFilter...");

        // 1. UID 라는 cookie를 가지고 있는가?
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        Cookie cookie = CookieUtils.getCookieByName(httpRequest.getCookies(), "UID");

        // 2. uid로 사용자 정보 조회
        if(cookie != null) {
            String uid = cookie.getValue();
            User user = userRepository.findByUid(uid);

            // 3. expireDate
            LocalDateTime now = LocalDateTime.now();
            if(now.isBefore(user.getExpireDate())) {

                // 4. Session 생성
                HttpSession session = httpRequest.getSession();
                session.setAttribute("loginInfo", new UserDto(user));
                session.setMaxInactiveInterval(60 * 30);

                // 5. cookie 만료 기간 갱신 및 DB 업데이트
                cookie.setMaxAge(60 * 60 * 24 * 14);
                userRepository.updateExpireDateByUid(uid, now.plusDays(14));

                // 6. 사용자가 이동하려고 했던 페이지로 리다이렉트
                log.info("자동로그인 성공");
                String requestURI = httpRequest.getRequestURI();
                //httpResponse.sendRedirect(requestURI);
            }
            // 기한 만료
            else {
                log.info("Login expireDate is over...");
                //httpResponse.sendRedirect("/login"); // 로그인 페이지로 이동: 이렇게 하면 localhost:8080/login 으로 요청을 보내는 거 아닌가
            }
        }
        // uid 없음
        else {
            log.info("No cookie with name [UID]...");
            //httpResponse.sendRedirect("/login"); // 로그인 페이지로 이동: 이렇게 하면 localhost:8080/login 으로 요청을 보내는 거 아닌가
        }
    }
}
