package com.accountbook.service;

import com.accountbook.domain.entity.ComCategory;
import com.accountbook.domain.entity.User;
import com.accountbook.domain.entity.Category;
import com.accountbook.domain.repository.ComCategoryRepository;
import com.accountbook.domain.repository.CategoryRepository;
import com.accountbook.domain.repository.UserRepository;
import com.accountbook.dto.user.CategoryRequest;
import com.accountbook.dto.user.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ComCategoryRepository comCategoryRepository;
    private final UserRepository userRepository;

    public List<CategoryDto> getUserCategoryList(String userId) {
        List<Category> categoryList = categoryRepository.getCategoryListByUserId(userId);
        List<CategoryDto> result = new ArrayList<>();

        for (Category category: categoryList) {
            result.add(new CategoryDto(category));
        }

        return result;
    }

    public CategoryDto getUserCategory(Long seq) {
        return new CategoryDto(categoryRepository.getCategory(seq));
    }

    public void addUserCategory(CategoryRequest request) {
        // 1. category 공통 코드
        ComCategory comCategory = getCategory(request);

        // 2. UserCategory 등록
        User user = userRepository.findById(request.getUserId()).get();
        Category category = Category.createUserCategory(user, comCategory);
        categoryRepository.addUserCategory(category);
    }

    public void updateUserCategory(Long seq, CategoryRequest request) {
        Category category = categoryRepository.getCategory(seq);

        // 1. category 공통 코드
        ComCategory comCategory = getCategory(request);

        // 2. Dirty Checking
        category.changeCategory(comCategory);
    }

    public void deleteUserCategory(Long seq) {
        Category category = categoryRepository.getCategory(seq);
        categoryRepository.deleteUserCategory(category);
    }

    /**
     * Category 공통 코드 엔티티 조회 및 반환
     * 존재하지 않는 경우, 새로 등록 후 반환
     * 존재하는 경우, 조회 후 반환
     * @param request
     * @return
     */
    private ComCategory getCategory(CategoryRequest request) {
        // 1. Category entity 존재하는 지 확인
        ComCategory comCategory = comCategoryRepository.findByNameAndEventType(request.getName(), request.getEventType());

        // 2. Category entity 존재하지 않는 경우, Category 생성 후 UserCategory 등록
        if (comCategory == null) {
            comCategory = ComCategory.createCategory(request);
            comCategoryRepository.save(comCategory);
        }

        return comCategory;
    }
}
