package com.accountbook.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * LoginCheckInterceptor
 *
 * @author donggun
 * @since 2022/02/28
 */
@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle (HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();

        log.info("Login Check for [{}]...", requestURI);

        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("loginInfo") == null) {
            log.info("Non-login User's Request...");

            response.sendRedirect("/login");
            return false;
        }

        return true;
    }

}
