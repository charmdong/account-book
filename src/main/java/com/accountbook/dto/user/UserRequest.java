package com.accountbook.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

/**
 * 사용자 등록 및 수정, 아이디 및 패스워드 찾기 등 요청 정보 전달 클래스
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    @NotEmpty(message = "아이디 누락")
    private String id;

    @NotEmpty(message = "패스워드 누락")
    private String password;

    @NotEmpty(message = "이름 누락")
    private String name;

    @NotEmpty(message = "이메일 누락")
    private String email;
    private LocalDateTime birthDate;
}
