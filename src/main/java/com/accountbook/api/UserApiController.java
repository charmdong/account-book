package com.accountbook.api;

import com.accountbook.common.utils.SessionUtils;
import com.accountbook.dto.response.ApiResponse;
import com.accountbook.dto.user.*;
import com.accountbook.exception.common.CommonResponseMessage;
import com.accountbook.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * 사용자 API Controller
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserApiController {

    private final UserService userService;

    /**
     * 사용자 등록
     * @param request
     * @return 추가된 사용자 정보
     */
    @PostMapping
    public ApiResponse addUser(@RequestBody @Valid UserCreateRequest request) throws Exception {

        UserDto createdUser = userService.addUser(request);

        return new ApiResponse(createdUser, HttpStatus.CREATED, CommonResponseMessage.SUCCESS);
    }

    /**
     * 사용자 조회
     * @param userId
     * @return 사용자 정보
     */
    @GetMapping("/{userId}")
    public ApiResponse getUser(@PathVariable("userId") String userId) throws Exception {

        return new ApiResponse(userService.getUser(userId), HttpStatus.OK, CommonResponseMessage.SUCCESS);
    }

    /**
     * 사용자 정보 수정
     * @param userId
     * @param request
     * @return 수정된 사용자 정보
     */
    @PutMapping("/{userId}")
    public ApiResponse updateUser(@PathVariable("userId") String userId, @RequestBody UserUpdateRequest request) throws Exception {

        userService.updateUser(userId, request);
        UserDto updatedUser = userService.getUser(userId);

        return new ApiResponse(updatedUser, HttpStatus.OK, CommonResponseMessage.SUCCESS);
    }

    /**
     * 사용자 패스워드 변경
     * @param userId
     * @param request
     * @return
     */
    @PatchMapping("/{userId}/password")
    public ApiResponse changePassword(@PathVariable("userId") String userId, @Valid @RequestBody PasswordRequest request) throws Exception {

        userService.changePassword(userId, request);
        return new ApiResponse(true, HttpStatus.OK, CommonResponseMessage.SUCCESS);
    }

    /**
     * 사용자 탈퇴
     * @param userId
     * @return 삭제 여부
     */
    @DeleteMapping("/{userId}")
    public ApiResponse deleteUser(@PathVariable("userId") String userId) throws Exception {

        userService.deleteUser(userId);

        // TODO 삭제 여부 어떻게 확인? Service 단에서 삭제 됐는 지 확인 후 예외 던지기?
        return new ApiResponse(true, HttpStatus.OK, CommonResponseMessage.SUCCESS);
    }

    /**
     * 사용자 아이디 찾기
     * @param request (name, email)
     * @return 사용자 아이디
     */
    @PostMapping("/id")
    public ApiResponse findUserId(@RequestBody UserInfoRequest request) throws Exception {

        return new ApiResponse(userService.findUserId(request), HttpStatus.OK, CommonResponseMessage.SUCCESS);
    }

    /**
     * 사용자 패스워드 찾기
     * @param request (id, email)
     * @return 사용자 패스워드
     */
    @PostMapping("/{userId}/password")
    public ApiResponse findUserPassword(@PathVariable String userId, @RequestBody UserInfoRequest request) throws Exception {
        // TODO 사용자 패스워드를 반환하면 안됨. 이메일로 쏘든지 해야함.
        // TODO userId도 확인하도록 수정
        return new ApiResponse(userService.findPassword(request), HttpStatus.OK, CommonResponseMessage.SUCCESS);
    }

    /**
     * 사용자 설정 정보 수정
     * @param session
     * @param request
     * @return
     * @throws Exception
     */
    @PatchMapping("/{userId}/setting")
    public ApiResponse updateCustomSetting(HttpSession session, @RequestBody UpdateSettingRequest request) throws Exception {
        LoginInfo loginInfo = (LoginInfo) session.getAttribute(SessionUtils.LOGIN_INFO);
        String userId = loginInfo.getUserId();

        return new ApiResponse(userService.updateCustomSetting(userId, request), HttpStatus.OK, CommonResponseMessage.SUCCESS);
    }
}
