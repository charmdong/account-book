package com.accountbook.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequest extends UserRequest {

    @NotEmpty(message = "패스워드 누락")
    private String password;

    @NotEmpty(message = "이름 누락")
    private String name;

    @NotEmpty(message = "이메일 누락")
    private String email;
    private LocalDateTime birthDate;
}
