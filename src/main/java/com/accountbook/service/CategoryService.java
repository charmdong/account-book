package com.accountbook.service;

import com.accountbook.domain.entity.ComCategory;
import com.accountbook.domain.entity.User;
import com.accountbook.domain.entity.Category;
import com.accountbook.domain.repository.category.ComCategoryRepository;
import com.accountbook.domain.repository.category.CategoryRepository;
import com.accountbook.domain.repository.user.UserRepository;
import com.accountbook.dto.category.CategoryRequest;
import com.accountbook.dto.category.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    public List<CategoryDto> getUserCategoryList(String userId) {

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
    public CategoryDto getUserCategory(Long seq) {

        return new CategoryDto(categoryRepository.getCategory(seq));
    }

    /**
     * 사용자 카테고리 등록
     *
     * @param request
     */
    public void addUserCategory(CategoryRequest request) {
        // 1. category 공통 코드
        ComCategory comCategory = getComCategory(request);

        // 2. UserCategory 등록
        User user = userRepository.findById(request.getUserId()).get();
        Category category = Category.createCategory(user, comCategory);
        categoryRepository.addCategory(category);
    }

    /**
     * 사용자 카테고리 정보 수정
     *
     * @param seq
     * @param request
     */
    public void updateUserCategory(Long seq, CategoryRequest request) {
        Category category = categoryRepository.getCategory(seq);

        // 1. category 공통 코드
        ComCategory comCategory = getComCategory(request);

        // 2. Dirty Checking
        category.changeComCategory(comCategory);
    }

    /**
     * 사용자 카테고리 삭제
     *
     * @param seq
     */
    public void deleteUserCategory(Long seq) {
        Category category = categoryRepository.getCategory(seq);
        categoryRepository.deleteCategory(category);
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
