package com.accountbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing
@SpringBootApplication
public class AccountBookApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountBookApplication.class, args);
	}

	/**
	 * LastModifiedBy 어노테이션이 붙은 필드를 가지고 있는 엔티티가 수정되는 경우 호출됨
	 * @return 사용자 ID
	 */
	@Bean
	public AuditorAware<String> auditorProvider() {
		// TODO Spring Security에 맞게 수정
		// HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		// HttpSession session = request.getSession();
		// String loginId = (String) session.getAttribute("loginId");

		return () -> Optional.of("test");
	}
}
