package com.accountbook.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordRequest {

    @NotEmpty(message = "패스워드가 누락되었습니다.")
    private String originPassword;

    @NotEmpty(message = "변경할 패스워드가 누락되었습니다.")
    private String newPassword;
}
