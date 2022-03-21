package com.accountbook.config;

import com.accountbook.interceptor.LogInterceptor;
import com.accountbook.interceptor.LoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors (InterceptorRegistry registry) {
        log.info("Init Interceptor...");
        registry.addInterceptor(new LogInterceptor()).order(1).addPathPatterns("/**");
        //registry.addInterceptor(loginInterceptor()).order(2).addPathPatterns("/**");

        //        registry.addInterceptor(new LoginCheckInterceptor())
        //                .order(2)
        //                .addPathPatterns("/**")
        //                .excludePathPatterns("/api/users/login");
    }

    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }
}
