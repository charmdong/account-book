package com.accountbook.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * LogTraceAspect
 *
 * @author donggun
 * @since 2022/05/06
 */
@Slf4j
@Aspect
public class LogTraceAspect {

    @Pointcut("execution(* com.accountbook.api..*.*(..))")
    private void allController() {}

    @Pointcut("execution(* com.accountbook.service..*.*(..))")
    private void allService() {}

    @Pointcut("execution(* com.accountbook.domain.repository..*.*(..))")
    private void allRepository() {}

    @Before("allController() || allService() || allRepository()")
    public void doLog(JoinPoint joinPoint) {

        // 전달인자
        Object[] args = joinPoint.getArgs();

        // 클래스명
        String declaringTypeName = joinPoint.getSignature().getDeclaringTypeName();
        declaringTypeName = declaringTypeName.substring(declaringTypeName.lastIndexOf(".") + 1);

        // 메서드명
        String methodName = joinPoint.getSignature().getName();

        log.info("[{}.{}] param={}", declaringTypeName, methodName, args);
    }
}
