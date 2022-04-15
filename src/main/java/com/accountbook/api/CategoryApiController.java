package com.accountbook.api;

import javax.servlet.http.HttpSession;

import com.accountbook.dto.response.ApiResponse;
import com.accountbook.exception.common.CommonResponseMessage;
import com.accountbook.service.CategoryService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryApiController {

    private final CategoryService categoryService;

    /**
     * 카테고리 목록 조회
     *
     * @param session
     * @return 
     */
    @GetMapping
    public ApiResponse getCategoryList(HttpSession session) throws Exception {

        return new ApiResponse(categoryService.getCategoryList(), HttpStatus.OK, CommonResponseMessage.SUCCESS);
    }

    @GetMapping("/rank/{user-id}")
    public ApiResponse getCategoryRank(@PathVariable("user-id") String userId) throws Exception {
        return new ApiResponse(categoryService.getCategoryRank(userId), HttpStatus.OK, CommonResponseMessage.SUCCESS);
    }

}
