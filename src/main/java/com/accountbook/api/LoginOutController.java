package com.accountbook.api;

import com.accountbook.dto.response.ApiResponse;
import com.accountbook.dto.user.UserDto;
import com.accountbook.exception.common.CommonResponseMessage;
import com.accountbook.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginOutController {

    private final UserService userService;

    /**
     * 로그인
     * @param userId
     * @param password
     * @param request
     * @return 사용자 아이디
     */
    @PostMapping("/login")
    public ApiResponse login (@RequestParam("userId") String userId,
                              @RequestParam("password") String password,
                              HttpServletRequest request, HttpServletResponse response) {

        UserDto loginInfo = userService.login(userId, password, request, response);

        return new ApiResponse(loginInfo.getId(), HttpStatus.OK, CommonResponseMessage.SUCCESS);
    }

    /**
     * 로그아웃
     * @param session
     * @return 사용자 아이디
     */
    @GetMapping("/logout")
    public ApiResponse logout (HttpSession session) {

        UserDto loginInfo = (UserDto) session.getAttribute("loginInfo");
        session.invalidate();
        return new ApiResponse(loginInfo.getId(), HttpStatus.OK, CommonResponseMessage.SUCCESS);
    }
}
