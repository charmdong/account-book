package com.accountbook.api;

import com.accountbook.dto.response.ApiResponse;
import com.accountbook.dto.user.*;
import com.accountbook.exception.common.CommonResponseMessage;
import com.accountbook.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * UserApiController
 *
 * @author donggun
 * @since 2022/04/17
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserApiController {

    private final UserService userService;

    /**
     * 사용자 등록
     *
     * @param request
     * @return 추가된 사용자 정보
     */
    @PostMapping
    public ApiResponse addUser (@RequestBody @Valid UserCreateRequest request) {

        UserDto createdUser = userService.addUser(request);

        return new ApiResponse(createdUser, HttpStatus.CREATED, CommonResponseMessage.SUCCESS);
    }

    /**
     * 사용자 조회
     *
     * @param userId
     * @return 사용자 정보
     */
    @GetMapping("/{userId}")
    public ApiResponse getUser (@PathVariable String userId) {
        return new ApiResponse(userService.getUser(userId), HttpStatus.OK, CommonResponseMessage.SUCCESS);
    }

    /**
     * 사용자 정보 수정
     *
     * @param userId
     * @param request
     * @return 수정된 사용자 정보
     */
    @PutMapping("/{userId}")
    public ApiResponse updateUser (@PathVariable String userId, @RequestBody UserUpdateRequest request) {

        userService.updateUser(userId, request);
        UserDto updatedUser = userService.getUser(userId);

        return new ApiResponse(updatedUser, HttpStatus.OK, CommonResponseMessage.SUCCESS);
    }

    /**
     * 사용자 패스워드 변경
     *
     * @param userId
     * @param request
     * @return
     */
    @PatchMapping("/password/{userId}")
    public ApiResponse changePassword (@PathVariable String userId, @Valid @RequestBody PasswordRequest request) {

        userService.changePassword(userId, request);
        return new ApiResponse(true, HttpStatus.OK, CommonResponseMessage.SUCCESS);
    }

    /**
     * 사용자 탈퇴
     *
     * @param userId
     * @return 삭제 여부
     */
    @DeleteMapping("/{userId}")
    public ApiResponse deleteUser (@PathVariable String userId) {

        userService.deleteUser(userId);

        // TODO 삭제 여부 어떻게 확인? Service 단에서 삭제 됐는지 확인 후 예외 던지기?
        return new ApiResponse(true, HttpStatus.OK, CommonResponseMessage.SUCCESS);
    }

    /**
     * 사용자 아이디 찾기
     *
     * @param request (name, email)
     * @return 사용자 아이디
     */
    @PostMapping("/id")
    public ApiResponse findUserId (@RequestBody UserInfoRequest request) {
        return new ApiResponse(userService.findUserId(request), HttpStatus.OK, CommonResponseMessage.SUCCESS);
    }

    /**
     * 사용자 패스워드 찾기
     *
     * @param userId
     * @param request
     * @return 사용자 패스워드
     */
    @PostMapping("/password/{userId}")
    public ApiResponse findUserPassword (@PathVariable String userId, @RequestBody UserInfoRequest request) {
        // TODO 이메일로 쏘든지 해야함.
        return new ApiResponse(userService.findPassword(userId, request), HttpStatus.OK, CommonResponseMessage.SUCCESS);
    }

    /**
     * 사용자 설정 정보 수정
     * @param userId
     * @param request
     * @return
     */
    @PatchMapping("/setting/{userId}")
    public ApiResponse updateCustomSetting (@PathVariable String userId, @RequestBody UpdateSettingRequest request) {
        return new ApiResponse(userService.updateCustomSetting(userId, request), HttpStatus.OK, CommonResponseMessage.SUCCESS);
    }
}
