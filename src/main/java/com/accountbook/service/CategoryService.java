package com.accountbook.service;

import com.accountbook.domain.entity.ComCategory;
import com.accountbook.domain.entity.User;
import com.accountbook.domain.entity.Category;
import com.accountbook.domain.repository.category.CategoryRepository;
import com.accountbook.domain.repository.category.ComCategoryRepository;
import com.accountbook.domain.repository.user.UserRepository;
import com.accountbook.dto.category.CategoryRequest;
import com.accountbook.dto.category.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * CategoryService
 *
 * @author donggun
 * @since 2021/11/15
 */
@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ComCategoryRepository comCategoryRepository;
    private final UserRepository userRepository;

    /**
     * 사용자 카테고리 목록 조회
     *
     * @param userId
     * @return 사용자 카테고리 목록
     */
    @Transactional(readOnly = true)
    public List<CategoryDto> getCategoryListByUser(String userId) {

        User user = userRepository.findById(userId).get();

        List<CategoryDto> result = categoryRepository
                .getCategoryListByUser(user)
                .orElseGet(ArrayList::new)
                .stream()
                .map(CategoryDto::new)
                .collect(Collectors.toList());

        return result;
    }

    /**
     * 사용자 카테고리 상세 조회
     *
     * @param seq
     * @return 사용자 카테고리 상세 정보
     */
    @Transactional(readOnly = true)
    public CategoryDto getCategory(Long seq) {

        return new CategoryDto(categoryRepository.findBySeq(seq));
    }

    /**
     * 사용자 카테고리 등록
     *
     * @param request
     */
    public Long addCategory(CategoryRequest request) {

        // 1. category 공통 코드 조회
        ComCategory comCategory = getComCategory(request);

        // 2. UserCategory 등록
        User user = userRepository.findById(request.getUserId()).get();
        Category category = Category.createCategory(user, comCategory);
        categoryRepository.addCategory(category);

        // 3. category seq 반환
        return category.getSeq();
    }

    /**
     * 사용자 카테고리 정보 수정
     *
     * @param seq
     * @param request
     */
    public CategoryDto updateCategory(Long seq, CategoryRequest request) {

        // 1. 사용자 category 조회
        Category category = categoryRepository.findBySeq(seq);

        // 2. category 공통 코드 조회
        ComCategory comCategory = getComCategory(request);

        // 3. Dirty Checking
        category.changeComCategory(comCategory);

        // 4. flush & find
        categoryRepository.flush();
        Category updatedCategory = categoryRepository.findBySeq(category.getSeq());

        // 5. category seq 반환
        return new CategoryDto(updatedCategory);
    }

    /**
     * 사용자 카테고리 삭제
     *
     * @param seq
     */
    public Boolean deleteCategory(Long seq, String userId) {

        // 사용자 조회
        User user = userRepository.findById(userId).get();

        // 사용자 카테고리 조회
        Category category = categoryRepository.findBySeq(seq);

        // 사용자 카테고리 목록 수정
        category.updateUserCategoryList(user);

        // 사용자 카테고리 삭제
        categoryRepository.deleteBySeq(seq);

        return categoryRepository.findBySeq(seq) == null;
    }

    /**
     * Category 공통 코드 엔티티 조회 및 반환
     * 존재하지 않는 경우, 새로 등록 후 반환
     * 존재하는 경우, 조회 후 반환
     *
     * @param request
     * @return 카테고리 공통 코드 정보
     */
    @Transactional(readOnly = true)
    private ComCategory getComCategory(CategoryRequest request) {

        ComCategory comCategory = comCategoryRepository
                                    .findByNameAndEventType(request.getName(), request.getEventType())
                                    .orElseGet(() -> ComCategory.createCategory(request));
        comCategoryRepository.save(comCategory);

        return comCategory;
    }
}
