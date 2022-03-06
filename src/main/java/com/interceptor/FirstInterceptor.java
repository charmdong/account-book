package com.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class FirstInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Controller 실행 전에 실행되는 메소드 입니다. return 값이 True면 컨트톨러로 진입, False일경우 진입하지 않는다.
        log.info("========== preHandler Start!! ==========");
        log.info("Request URL : {}", request.getRequestURI());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // Controller 실행 후 View가 랜더링 되기 전에 실행
        log.info("========== postHandler Start!! ==========");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // Controller 실행 후 View가 랜더링 된 후에 실행
        log.info("========== afterCompletion Start!! ==========");
    }
}
