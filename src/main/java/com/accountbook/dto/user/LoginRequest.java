package com.accountbook.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class LoginRequest {

    @NotEmpty(message = "아이디가 누락되었습니다.")
    private String userId;

    @NotEmpty(message = "패스워드가 누락되었습니다.")
    private String password;
}
