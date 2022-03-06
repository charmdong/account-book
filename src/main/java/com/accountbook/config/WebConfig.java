package com.accountbook.config;

import com.accountbook.interceptor.LogInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebConfig
 *
 * @author donggun
 * @since 2022/02/28
 */
@Slf4j
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors (InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor()).order(1).addPathPatterns("/**");

        //        registry.addInterceptor(new LoginCheckInterceptor())
        //                .order(2)
        //                .addPathPatterns("/**")
        //                .excludePathPatterns("/api/users/login");
    }
}
