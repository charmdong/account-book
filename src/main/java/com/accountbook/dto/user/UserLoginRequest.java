package com.accountbook.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRequest {

    @NotEmpty(message = "아이디 누락")
    private String userId;

    @NotEmpty(message = "패스워드 누락")
    private String password;
}
