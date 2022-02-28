package com.accountbook.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * LogInterceptor
 *
 * @author donggun
 * @since 2022/02/28
 */
@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    private static final String REQ_UUID = "requestUUID";

    @Override
    public boolean preHandle (HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        String uuid = UUID.randomUUID().toString();
        request.setAttribute(REQ_UUID, uuid);

        log.info("REQUEST \tUUID:[{}]\tURI:[{}]\t[{}]", uuid, requestURI, handler);
        return true;
    }

    @Override
    public void afterCompletion (HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        String requestURI = request.getRequestURI();
        String uuid = (String) request.getAttribute(REQ_UUID);

        log.info("RESPONSE \tUUID:[{}]\tURI:[{}]", uuid, requestURI);

        if(ex != null) {
            log.error("An ERROR has occurred...!", ex);
        }
    }
}
