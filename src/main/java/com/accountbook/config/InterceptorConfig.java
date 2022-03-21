package com.accountbook.config;

import com.accountbook.interceptor.LogInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors (InterceptorRegistry registry) {
        log.info("Init Interceptor...");

        // log
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**");

    }

}
