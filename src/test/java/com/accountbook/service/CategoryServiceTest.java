package com.accountbook.service;

import com.accountbook.domain.entity.Category;
import com.accountbook.domain.entity.ComCategory;
import com.accountbook.domain.entity.User;
import com.accountbook.domain.enums.EventType;
import com.accountbook.domain.repository.category.CategoryRepository;
import com.accountbook.domain.repository.category.ComCategoryRepository;
import com.accountbook.domain.repository.user.UserRepository;
import com.accountbook.dto.category.CategoryDto;
import com.accountbook.dto.category.CategoryRequest;
import com.accountbook.dto.user.UserRequest;
import com.accountbook.exception.category.CategoryNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CategoryServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ComCategoryRepository comCategoryRepository;

    @Before
    public void before() throws Exception {

        UserRequest request = new UserRequest();

        request.setId("test1");
        request.setPassword("password1");
        request.setName("Donggun");
        request.setEmail("chungdk1993@naver.com");
        request.setBirthDate(Year.of(1993).atMonth(11).atDay(17).atTime(14,59));

        userService.addUser(request);
    }

    @Test
    public void insertCategoryTest() throws Exception {
        // given
        CategoryRequest categoryRequest = new CategoryRequest();

        categoryRequest.setUserId("test1");
        categoryRequest.setName("chicken");
        categoryRequest.setEventType(EventType.EXPENDITURE);
        categoryRequest.setUseYn(true);

        Long categorySeq = categoryService.addCategory(categoryRequest);

        // when
        CategoryDto category = categoryService.getCategory(categorySeq);

        // then
        assertThat(category.getName()).isEqualTo("chicken");
    }

    @Test
    public void updateCategoryTest() throws Exception {
        // given
        CategoryRequest categoryRequest = new CategoryRequest();

        categoryRequest.setUserId("test1");
        categoryRequest.setName("chicken");
        categoryRequest.setEventType(EventType.EXPENDITURE);
        categoryRequest.setUseYn(true);

        Long categorySeq = categoryService.addCategory(categoryRequest);

        // when
        CategoryRequest request = new CategoryRequest();
        request.setName("pizza");

        CategoryDto updateCategory = categoryService.updateCategory(categorySeq, request);

        // then
        assertThat(updateCategory.getName()).isEqualTo("pizza");
    }

    @Test
    public void deleteCategoryTest() throws Exception {
        // given
        CategoryRequest categoryRequest = new CategoryRequest();

        categoryRequest.setUserId("test1");
        categoryRequest.setName("chicken");
        categoryRequest.setEventType(EventType.EXPENDITURE);
        categoryRequest.setUseYn(true);

        Long categorySeq = categoryService.addCategory(categoryRequest);

        // when
        List<CategoryDto> categoryList = categoryService.getCategoryListByUser("test1");
        Long seq = categoryList.get(0).getSeq();
        categoryService.deleteCategory(seq, "test1");

        // then
        assertThrows(CategoryNotFoundException.class,
                () -> categoryService.getCategory(seq));
    }
}