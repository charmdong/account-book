package com.accountbook;

import com.accountbook.aop.LogTraceAspect;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.persistence.EntityManager;
import java.util.Optional;

@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication
@Import(LogTraceAspect.class)
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
		// HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		// HttpSession session = request.getSession();
		// String loginId = (String) session.getAttribute("loginId");

		return () -> Optional.of("test");
	}

	@Bean
	public JPAQueryFactory jpaQueryFactory(EntityManager em) {
		return new JPAQueryFactory(em);
	}
}
