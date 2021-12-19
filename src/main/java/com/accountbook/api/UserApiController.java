package com.accountbook.api;

import com.accountbook.dto.user.UserRequest;
import com.accountbook.dto.asset.ApiResponse;
import com.accountbook.dto.user.UserDto;
import com.accountbook.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

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
    public ApiResponse addUser(@RequestBody @Valid UserRequest request) throws Exception {

        userService.addUser(request);
        UserDto createdUser = userService.getUser(request.getId());

        return new ApiResponse(createdUser, HttpStatus.OK, "SUCCESS");
    }

    /**
     * 사용자 조회
     * @param userId
     * @return 사용자 정보
     */
    @GetMapping("/{userId}")
    public ApiResponse getUser(@PathVariable("userId") String userId) throws Exception {

        return new ApiResponse(userService.getUser(userId), HttpStatus.OK, "SUCCESS");
    }

    /**
     * 사용자 정보 수정
     * @param userId
     * @param request
     * @return 수정된 사용자 정보
     */
    @PutMapping("/{userId}")
    public ApiResponse updateUser(@PathVariable("userId") String userId, @RequestBody UserRequest request) throws Exception {

        userService.updateUser(userId, request);
        UserDto updatedUser = userService.getUser(userId);

        return new ApiResponse(updatedUser, HttpStatus.OK, "SUCCESS");
    }

    /**
     * 사용자 패스워드 변경
     * @param userId
     * @param request
     * @return
     */
    @PutMapping("/password/{userId}")
    public ApiResponse changePassword(@PathVariable("userId") String userId, @RequestBody UserRequest request) throws Exception {

        userService.changePassword(userId, request.getPassword());
        return new ApiResponse(true, HttpStatus.OK, "SUCCESS");
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
        return new ApiResponse("", HttpStatus.OK, "SUCCESS");
    }

    /**
     * 사용자 아이디 찾기
     * @param request
     * @return 사용자 아이디
     */
    @PostMapping("/id")
    public ApiResponse findUserId(@RequestBody UserRequest request) throws Exception {

        return new ApiResponse(userService.findUserId(request), HttpStatus.OK, "SUCCESS");
    }

    /**
     * 사용자 패스워드 찾기
     * @param request
     * @return 사용자 패스워드
     */
    @PostMapping("/password")
    public ApiResponse findUserPassword(@RequestBody UserRequest request) throws Exception {
        // TODO 사용자 패스워드를 반환하면 안됨. 이메일로 쏘든지 해야함.
        return new ApiResponse(userService.findPassword(request), HttpStatus.OK, "SUCCESS");
    }

}
