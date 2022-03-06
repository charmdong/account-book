package com.accountbook.api;

import com.accountbook.dto.response.ApiResponse;
import com.accountbook.dto.user.UserDto;
import com.accountbook.dto.user.UserLoginRequest;
import com.accountbook.exception.common.CommonResponseMessage;
import com.accountbook.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/login")
public class LoginApiController {

    private final UserService userService;

    @PostMapping
    public ApiResponse login(HttpServletRequest httpServletRequest, @Valid UserLoginRequest request) throws Exception {
        UserDto user = userService.getUser(request.getUserId());
        HttpSession httpSession = httpServletRequest.getSession();
        httpSession.setAttribute("user", user);
        return new ApiResponse(true, HttpStatus.OK, CommonResponseMessage.SUCCESS);
    }

    @GetMapping
    public ApiResponse logout(HttpServletRequest httpServletRequest, @Valid UserLoginRequest request) throws Exception {
        HttpSession httpSession = httpServletRequest.getSession();
        httpSession.invalidate();
        return new ApiResponse(true, HttpStatus.OK, CommonResponseMessage.SUCCESS);
    }
}
