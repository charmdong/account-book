package com.accountbook.api;

import com.accountbook.dto.category.CategoryRequest;
import com.accountbook.dto.category.CategoryDto;
import com.accountbook.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

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
    public List<CategoryDto> getUserCategoryList(HttpSession session) {
        // TODO SessionAttribute 사용방법 알아보기
        String userId = (String) session.getAttribute("userId");
        return categoryService.getUserCategoryList(userId);
    }

    /**
     * 사용자 카테고리 상세 조회
     *
     * @param seq
     * @return 사용자 카테고리 상세 정보
     */
    @GetMapping("/{userCategorySeq}")
    public CategoryDto getUserCategory(@PathVariable("userCategorySeq") Long seq) {

        return categoryService.getUserCategory(seq);
    }

    /**
     * 사용자 카테고리 등록
     *
     * @param request
     */
    @PostMapping
    public void addUserCategory(@RequestBody @Valid CategoryRequest request) {

        categoryService.addUserCategory(request);
    }

    /**
     * 사용자 카테고리 정보 수정
     *
     * @param seq
     * @param request
     */
    @PatchMapping("/{userCategorySeq}")
    public void updateUserCategory(@PathVariable("userCategorySeq") Long seq,
                                   @RequestBody @Valid CategoryRequest request) {

        categoryService.updateUserCategory(seq, request);
    }

    /**
     * 사용자 카테고리 삭제
     *
     * @param seq
     */
    @DeleteMapping("/{userCategorySeq}")
    public void deleteUserCategory(@PathVariable("userCategory") Long seq) {

        categoryService.deleteUserCategory(seq);
    }
}
