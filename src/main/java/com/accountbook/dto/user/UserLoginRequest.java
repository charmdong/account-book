package com.accountbook.dto.user;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 로그인
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRequest {

    @NotEmpty(message = "아이디 누락")
    private String id;

    @NotEmpty(message = "패스워드 누락")
    private String password;

}
