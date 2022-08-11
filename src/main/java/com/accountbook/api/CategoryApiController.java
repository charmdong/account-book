package com.accountbook.api;

import com.accountbook.dto.category.CategoryRequest;
import com.accountbook.dto.response.ApiResponse;
import com.accountbook.exception.common.CommonResponseMessage;
import com.accountbook.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryApiController {

    private final CategoryService categoryService;

    // 카테고리 목록 조회
    @GetMapping("/list")
    public ApiResponse getCategoryList(@RequestParam("useYn") String useYn) throws Exception {

        return new ApiResponse(categoryService.getCategoryList(useYn), HttpStatus.OK, CommonResponseMessage.SUCCESS);
    }

    // 카테고리 목록 조회 (이벤트 포함)
    @GetMapping("/event-list")
    public ApiResponse getCategoryEventList() throws Exception {

        return new ApiResponse(categoryService.getCategoryEventList(), HttpStatus.OK, CommonResponseMessage.SUCCESS);
    }
    
    // 특정 카테고리 조회
    @GetMapping("/{category-seq}")
    public ApiResponse getCategory(@PathVariable long categorySeq) throws Exception {

        return new ApiResponse(categoryService.getCategory(categorySeq), HttpStatus.OK, CommonResponseMessage.SUCCESS);
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
    @PatchMapping("/{category-seq}")
    public ApiResponse update(@RequestBody @Valid CategoryRequest categoryRequest, @PathVariable long categorySeq) throws Exception {
        return new ApiResponse(categoryService.updateCategory(categoryRequest, categorySeq), HttpStatus.OK, CommonResponseMessage.SUCCESS);
    }

    //카테고리 삭제
    @DeleteMapping("/{category-seq}")
    public ApiResponse delete(@PathVariable long categorySeq) throws Exception {
        return new ApiResponse(categoryService.deleteCategory(categorySeq), HttpStatus.OK, CommonResponseMessage.SUCCESS);
    }
}