package com.accountbook.exception.user;

import com.accountbook.api.UserApiController;
import com.accountbook.dto.asset.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * UserExceptionHandler
 *
 * @author donggun
 * @since 2021/12/16
 */
@RestControllerAdvice(basePackageClasses = {UserApiController.class})
public class UserExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ApiResponse userExceptionHandler (UserException ue) {

        return new ApiResponse(
                ue.getUserExceptionCode().getCode(),
                HttpStatus.OK,
                ue.getUserExceptionCode().getMessage()
        );
    }

}
