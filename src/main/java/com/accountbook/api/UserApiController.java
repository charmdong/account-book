package com.accountbook.api;

import com.accountbook.dto.user.UserRequest;
import com.accountbook.dto.user.UserDto;
import com.accountbook.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserApiController {

    private final UserService userService;

    @PostMapping("")
    public void join(@RequestBody UserRequest userRequest) {

    }

    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable("userId") String userId) {

        return new UserDto();
    }

    @PutMapping("/{userId}")
    public void updateUser(@PathVariable("userId") String userId, @RequestBody UserRequest userRequest) {

    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") String userId) {

    }

    @PostMapping("/id")
    public String findUserId(@RequestBody UserRequest request) {

        return "";
    }

    @PostMapping("/password")
    public String findUserPassword(@RequestBody UserRequest request) {

        return "";
    }
}
