package com.accountbook.api;

import com.accountbook.dto.EcoEvent.EcoEventRequest;
import com.accountbook.dto.category.CategoryRequest;
import com.accountbook.dto.response.ApiResponse;
import com.accountbook.exception.common.CommonResponseMessage;
import com.accountbook.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


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

    //카테고리 등록
    @PostMapping
    public ApiResponse create(@RequestBody @Valid CategoryRequest categoryRequest) throws Exception {
        return new ApiResponse(categoryService.enrollCategory(categoryRequest), HttpStatus.OK, CommonResponseMessage.SUCCESS);
    }

    //카테고리 수정
    @PatchMapping("/{categorySeq}")
    public ApiResponse update(@RequestBody @Valid CategoryRequest categoryRequest, @PathVariable long categorySeq) throws Exception {
        return new ApiResponse(categoryService.updateCategory(categoryRequest, categorySeq), HttpStatus.OK, CommonResponseMessage.SUCCESS);
    }

    //카테고리 삭제
    @DeleteMapping("/{categorySeq}")
    public ApiResponse delete(@PathVariable long categorySeq) throws Exception {
        return new ApiResponse(categoryService.deleteCategory(categorySeq), HttpStatus.OK, CommonResponseMessage.SUCCESS);
    }
}