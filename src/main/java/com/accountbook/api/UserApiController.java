package com.accountbook.api;

import com.accountbook.dto.user.UserRequest;
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
     */
    @PostMapping
    public void addUser(@RequestBody @Valid UserRequest request) {

        userService.addUser(request);
    }

    /**
     * 사용자 조회
     * @param userId
     * @return 사용자 정보
     */
    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable("userId") String userId) {

        return userService.getUser(userId);
    }

    /**
     * 사용자 정보 수정
     * @param userId
     * @param request
     */
    @PutMapping("/{userId}")
    public void updateUser(@PathVariable("userId") String userId, @RequestBody @Valid UserRequest request) {

        userService.updateUser(userId, request);
    }

    /**
     * 사용자 탈퇴
     * @param userId
     */
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") String userId) {

        userService.deleteUser(userId);
    }

    /**
     * 사용자 아이디 찾기
     * @param request
     * @return 사용자 아이디
     */
    @PostMapping("/id")
    public String findUserId(@RequestBody UserRequest request) {

        return userService.findUserId(request);
    }

    /**
     * 사용자 패스워드 찾기
     * @param request
     * @return 사용자 패스워드
     */
    @PostMapping("/password")
    public String findUserPassword(@RequestBody UserRequest request) {
        // TODO 사용자 패스워드를 반환하면 안됨. 이메일로 쏘든지 해야함.
        return userService.findPassword(request);
    }

    /**
     * @Valid Exception Handler
     *
     * @param e
     * @return
     */
    @ExceptionHandler
    public ResponseEntity validExceptionHandler(MethodArgumentNotValidException e) {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("message", e.getBindingResult().getFieldError().getDefaultMessage());

        return new ResponseEntity(resultMap, HttpStatus.BAD_REQUEST);
    }
}
