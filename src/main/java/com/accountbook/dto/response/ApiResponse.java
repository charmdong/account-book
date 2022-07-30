package com.accountbook.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

    private T data; // 응답 데이터 (오류 발생의 경우 오류 코드)
    private HttpStatus status;
    private String message;
    
}
