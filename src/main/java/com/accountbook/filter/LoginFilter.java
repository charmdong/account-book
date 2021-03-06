package com.accountbook.filter;

import com.accountbook.common.utils.CookieUtils;
import com.accountbook.common.utils.SessionUtils;
import com.accountbook.domain.entity.User;
import com.accountbook.domain.repository.user.UserRepository;
import com.accountbook.dto.user.LoginInfo;
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
    private UserRepository userRepository;

    private static final String REDIRECT_URL = "/login.html";

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
        Cookie cookie = CookieUtils.getCookieByName(httpRequest.getCookies(), CookieUtils.LOGIN_CHECK_COOKIE);

        // 2. 세션 정보 확인
        HttpSession session = httpRequest.getSession(false);
        if (session != null) {
            chain.doFilter(request, response);
        }

        // 3. uid로 사용자 정보 조회
        if (cookie != null) {
            String token = cookie.getValue();
            User user = userRepository.findByToken(token);

            // 3.1. check loginIp
            String requestIp = httpRequest.getRemoteAddr();
            String loginIp = user.getLoginIp();
            if (!loginIp.equals(requestIp)) {
                log.info("Incorrect Ip...");
                httpResponse.sendRedirect(REDIRECT_URL);
            }

            // 3.2. expireDate 유효성 판단
            LocalDateTime now = LocalDateTime.now();
            if (now.isBefore(user.getExpireDate())) {

                // 3.2.1. Session 생성
                HttpSession userSession = httpRequest.getSession();
                userSession.setAttribute(SessionUtils.LOGIN_INFO, new LoginInfo(user));
                userSession.setMaxInactiveInterval(SessionUtils.SESSION_TIME);

                // 3.2.2. cookie 만료 기간 갱신 및 DB 업데이트
                cookie.setMaxAge(CookieUtils.COOKIE_MAX_AGE);
                userRepository.updateExpireDateByToken(token, now.plusDays(CookieUtils.PLUS_DAY));

                // 3.2.3. 사용자 요청 처리
                log.info("Login...");
                String requestURI = httpRequest.getRequestURI();
                chain.doFilter(request, response);
            }
            // 3.3. 기한 만료
            else {
                log.info("Login expireDate is over...");
                httpResponse.sendRedirect(REDIRECT_URL);
            }
        }
        // 4. uid 없음
        else {
            log.info("Non-login User...");
            httpResponse.sendRedirect(REDIRECT_URL);
        }
    }
}
