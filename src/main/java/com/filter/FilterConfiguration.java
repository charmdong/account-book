package com.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FilterConfiguration implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean firstFilterRegister() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(new FirstFilter());
        registrationBean.setOrder(1);
        // “setUrlPatterns” 와 “addUrlPatterns” 의 차이는 list 자체를 받을건지 아니면 가변인자로 계속 추가 할것인지.
        registrationBean.addUrlPatterns("/api/asset");
        return registrationBean;
    }
}
