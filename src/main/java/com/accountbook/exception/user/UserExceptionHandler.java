package com.accountbook.exception.user;

import com.accountbook.api.UserApiController;
import com.accountbook.dto.response.ApiResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * UserExceptionHandler
 *
 * @author donggun
 * @since 2021/12/16
 */
@Slf4j
@RestControllerAdvice(basePackageClasses = {UserApiController.class})
@Order(Ordered.HIGHEST_PRECEDENCE)
public class UserExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ApiResponse userExceptionHandler (UserException ue) {

        log.warn("{}...{}:{}", ue.getClass(), ue.getUserExceptionCode(), ue.getUserExceptionCode().getMessage(), ue);

        return new ApiResponse(
                ue.getUserExceptionCode().getCode(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                ue.getUserExceptionCode().getMessage()
        );
    }

}
