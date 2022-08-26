package com.accountbook.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequest extends UserRequest {

    //@NotEmpty(message = "패스워드 누락")
    private String password;

    //@NotEmpty(message = "이름 누락")
    private String name;

    //@NotEmpty(message = "이메일 누락")
    private String email;
    private String birthDate;
}
