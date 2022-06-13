package com.accountbook.config;

import com.accountbook.filter.LoginFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

/**
 * FilterConfig
 *
 * @author donggun
 * @since 2022/03/21
 */
@Slf4j
@Configuration
public class FilterConfig implements WebMvcConfigurer {

    //@Bean
    public FilterRegistrationBean loginFilterBean() {
        // 스프링 부트 사용하는 경우 FilterRegistrationBean 이용
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();

        filterRegistrationBean.setFilter(loginFilter()); // 필터 등록
        filterRegistrationBean.setOrder(1); // 순서 등록

        // 필터를 적용할 URL 패턴 지정. 여러개 한 번에 지정 가능
        filterRegistrationBean.addUrlPatterns("/api/*");

        return filterRegistrationBean;
    }

    // LoginFilter에서 UserRepository 의존관계 주입을 받기 위해서는 스프링 컨테이너에 먼저 등록이 되어있어야 한다.
    @Bean
    public LoginFilter loginFilter() {
        return new LoginFilter();
    }

}
