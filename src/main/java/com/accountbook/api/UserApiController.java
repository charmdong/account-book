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
    public void addUser(@RequestBody UserRequest request) {

        userService.addUser(request);
    }

    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable("userId") String userId) {

        return userService.getUser(userId);
    }

    @PutMapping("/{userId}")
    public void updateUser(@PathVariable("userId") String userId, @RequestBody UserRequest request) {

        userService.updateUser(userId, request);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") String userId) {

        userService.deleteUser(userId);
    }

    @PostMapping("/id")
    public String findUserId(@RequestBody UserRequest request) {

        return userService.findUserId(request);
    }

    @PostMapping("/password")
    public String findUserPassword(@RequestBody UserRequest request) {

        return userService.findPassword(request);
    }
}
