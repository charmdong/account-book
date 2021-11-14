package com.accountbook.service;

import com.accountbook.domain.entity.Category;
import com.accountbook.domain.entity.User;
import com.accountbook.domain.entity.UserCategory;
import com.accountbook.domain.repository.CategoryRepository;
import com.accountbook.domain.repository.UserCategoryRepository;
import com.accountbook.domain.repository.UserRepository;
import com.accountbook.dto.user.CategoryRequest;
import com.accountbook.dto.user.UserCategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCategoryService {

    private final UserCategoryRepository userCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public List<UserCategoryDto> getUserCategoryList(String userId) {
        List<UserCategory> categoryList = userCategoryRepository.getCategoryListByUserId(userId);
        List<UserCategoryDto> result = new ArrayList<>();

        for (UserCategory category: categoryList) {
            result.add(new UserCategoryDto(category));
        }

        return result;
    }

    public UserCategoryDto getUserCategory(Long seq) {
        return new UserCategoryDto(userCategoryRepository.getCategory(seq));
    }

    public void addUserCategory(CategoryRequest request) {
        // 1. category 공통 코드
        Category category = getCategory(request);

        // 2. UserCategory 등록
        User user = userRepository.findById(request.getUserId()).get();
        UserCategory userCategory = UserCategory.createUserCategory(user, category);
        userCategoryRepository.addUserCategory(userCategory);
    }

    public void updateUserCategory(Long seq, CategoryRequest request) {
        UserCategory userCategory = userCategoryRepository.getCategory(seq);

        // 1. category 공통 코드
        Category category = getCategory(request);

        // 2. Dirty Checking
        userCategory.changeCategory(category);
    }

    public void deleteUserCategory(Long seq) {
        UserCategory userCategory = userCategoryRepository.getCategory(seq);
        userCategoryRepository.deleteUserCategory(userCategory);
    }

    /**
     * Category 공통 코드 엔티티 조회 및 반환
     * 존재하지 않는 경우, 새로 등록 후 반환
     * 존재하는 경우, 조회 후 반환
     * @param request
     * @return
     */
    private Category getCategory(CategoryRequest request) {
        // 1. Category entity 존재하는 지 확인
        Category category = categoryRepository.findByNameAndEventType(request.getName(), request.getEventType());

        // 2. Category entity 존재하지 않는 경우, Category 생성 후 UserCategory 등록
        if (category == null) {
            category = Category.createCategory(request);
            categoryRepository.save(category);
        }

        return category;
    }
}
