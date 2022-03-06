package com.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.FilterConfig;
import java.io.IOException;

@Slf4j
@Component
public class FirstFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("============= filter init!! =============");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("============= filter 시작!=============");
        chain.doFilter(request,response);
        log.info("============= filter 종료!=============");
    }

    @Override
    public void destroy() {
        log.info("============= filter destroy!! =============");
    }
}
