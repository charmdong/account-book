package com.accountbook.api;

import com.accountbook.domain.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    private final UserRepository userRepository;
    private static final String SESSION_ID = "SESSION_ID";

    @GetMapping
    public String home(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            String name = cookie.getName();
            String value = cookie.getValue();

            if(SESSION_ID.equals(name)) {
                // session 으로 로그인
            }
        }

        return "login";
    }

}
