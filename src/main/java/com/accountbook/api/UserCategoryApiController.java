package com.accountbook.api;

import com.accountbook.dto.user.UserCategoryDto;
import com.accountbook.service.UserCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class UserCategoryApiController {

    private final UserCategoryService categoryService;

    @GetMapping
    public List<UserCategoryDto> getUserCategoryList() {
        return null;
    }
}
