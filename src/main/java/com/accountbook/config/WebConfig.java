package com.accountbook.config;

import com.accountbook.filter.LoginFilter;
import com.accountbook.interceptor.LogInterceptor;
import com.accountbook.interceptor.LoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

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

    @Bean
    public FilterRegistrationBean loginFilterBean() {
        // 스프링 부트 사용하는 경우 FilterRegistrationBean 이용
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();

        filterRegistrationBean.setFilter(loginFilter()); // 필터 등록
        filterRegistrationBean.setOrder(1); // 순서 등록

        // 필터를 적용할 URL 패턴 지정. 여러개 한 번에 지정 가능
        filterRegistrationBean.addUrlPatterns("/api/users/*");

        return filterRegistrationBean;
    }

    @Bean
    public LoginFilter loginFilter() {
        return new LoginFilter();
    }
}
