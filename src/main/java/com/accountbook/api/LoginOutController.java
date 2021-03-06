package com.accountbook.api;

import com.accountbook.common.utils.SessionUtils;
import com.accountbook.dto.response.ApiResponse;
import com.accountbook.dto.user.LoginInfo;
import com.accountbook.dto.user.LoginRequest;
import com.accountbook.exception.common.CommonResponseMessage;
import com.accountbook.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
     * @param loginRequest
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/login")
    public ApiResponse login (@RequestBody LoginRequest loginRequest,
                              HttpServletRequest request, HttpServletResponse response) {

        LoginInfo loginInfo = userService.login(loginRequest.getUserId(), loginRequest.getPassword(),
                                request, response);

        return new ApiResponse(loginInfo.getUserId(), HttpStatus.OK, CommonResponseMessage.SUCCESS);
    }

    /**
     * 로그아웃
     * @param session
     * @return 사용자 아이디
     */
    @GetMapping("/logout")
    public ApiResponse logout (HttpSession session) {

        LoginInfo loginInfo = (LoginInfo) session.getAttribute(SessionUtils.LOGIN_INFO);
        session.invalidate();
        return new ApiResponse(loginInfo.getUserId(), HttpStatus.OK, CommonResponseMessage.SUCCESS);
    }
}
