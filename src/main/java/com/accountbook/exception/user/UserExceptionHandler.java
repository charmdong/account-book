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
                ue.getUserExceptionCode().getCode(), // 에러 코드는 여기서 내려주고
                HttpStatus.OK, // 예외를 처리한 거니까 통신은 정상적이지 않은가?
                ue.getUserExceptionCode().getMessage()
        );
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse otherExceptionHandler (Exception e) {

        return new ApiResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.OK,
                "죄송해요;"
        );
    }
}
