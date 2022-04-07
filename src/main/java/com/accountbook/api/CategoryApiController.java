package com.accountbook.api;

import com.accountbook.dto.category.CategoryRequest;
import com.accountbook.dto.response.ApiResponse;
import com.accountbook.dto.user.UserDto;
import com.accountbook.exception.common.CommonResponseMessage;
import com.accountbook.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * CategoryApiController
 *
 * @author donggun
 * @since 2021/11/15
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryApiController {

    private final CategoryService categoryService;

    /**
     * 사용자 카테고리 목록 조회
     *
     * @param session
     * @return 사용자 카테고리 목록
     */
    @GetMapping
    public ApiResponse getCategoryListByUser(HttpSession session) throws Exception {

        UserDto loginInfo = (UserDto) session.getAttribute("loginInfo");
        String userId = loginInfo.getId();
        return new ApiResponse(categoryService.getCategoryListByUser(userId), HttpStatus.OK, CommonResponseMessage.SUCCESS);
    }

    /**
     * 사용자 카테고리 상세 조회
     *
     * @param seq
     * @return 사용자 카테고리 상세 정보
     */
    @GetMapping("/{categorySeq}")
    public ApiResponse getCategory(@PathVariable("categorySeq") Long seq) throws Exception {

        return new ApiResponse(categoryService.getCategory(seq), HttpStatus.OK, CommonResponseMessage.SUCCESS);
    }

    /**
     * 사용자 카테고리 등록
     *
     * @param request
     */
    @PostMapping
    public ApiResponse addCategory(@RequestBody @Validated CategoryRequest request) throws Exception {

        return new ApiResponse(categoryService.addCategory(request), HttpStatus.CREATED, CommonResponseMessage.SUCCESS);
    }

    /**
     * 사용자 카테고리 정보 수정
     *
     * @param seq
     * @param request
     */
    @PatchMapping("/{categorySeq}")
    public ApiResponse updateCategory(@PathVariable("categorySeq") Long seq,
                                   @RequestBody CategoryRequest request) throws Exception {

        return new ApiResponse(categoryService.updateCategory(seq, request), HttpStatus.OK, CommonResponseMessage.SUCCESS);
    }

    /**
     * 사용자 카테고리 삭제
     *
     * @param seq
     */
    @DeleteMapping("/{categorySeq}")
    public ApiResponse deleteCategory(@PathVariable("categorySeq") Long seq, HttpSession session) throws Exception {

        UserDto loginInfo = (UserDto) session.getAttribute("loginInfo");
        String userId = loginInfo.getId();
        return new ApiResponse(categoryService.deleteCategory(seq, userId), HttpStatus.OK, CommonResponseMessage.SUCCESS);
    }

}
