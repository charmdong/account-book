package com.accountbook.api;

import com.accountbook.dto.user.CategoryRequest;
import com.accountbook.dto.user.UserCategoryDto;
import com.accountbook.service.UserCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{userCategorySeq}")
    public UserCategoryDto getUserCategory(@PathVariable("userCategorySeq") Long seq) {
        return null;
    }

    @PostMapping
    public void addUserCategory(@RequestBody CategoryRequest request) {
        ;
    }

    @PatchMapping("/{userCategorySeq}")
    public void updateUserCategory(@PathVariable("userCategorySeq") Long seq,
                                   @RequestBody CategoryRequest request) {
        ;
    }

    @DeleteMapping("//{userCategorySeq}")
    public void deleteUserCategory(@PathVariable("userCategory") Long seq) {
        ;
    }
}
