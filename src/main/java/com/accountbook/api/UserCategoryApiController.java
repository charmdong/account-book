package com.accountbook.api;

import com.accountbook.dto.user.CategoryRequest;
import com.accountbook.dto.user.CategoryDto;
import com.accountbook.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class UserCategoryApiController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> getUserCategoryList() {
        return null;
    }

    @GetMapping("/{userCategorySeq}")
    public CategoryDto getUserCategory(@PathVariable("userCategorySeq") Long seq) {
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
