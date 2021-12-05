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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.List;

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

    @Test
    @Rollback(value = false)
    public void addCategoryTest () throws Exception {
        // given
        UserRequest request = new UserRequest();

        request.setId("test1");
        request.setPassword("password1");
        request.setName("Donggun");
        request.setEmail("chungdk1993@naver.com");
        request.setBirthDate(Year.of(1993).atMonth(11).atDay(17).atTime(14,59));

        userService.addUser(request);

        CategoryRequest categoryRequest = new CategoryRequest();

        categoryRequest.setUserId("test1");
        categoryRequest.setName("chicken");
        categoryRequest.setEventType(EventType.EXPENDITURE);
        categoryRequest.setUseYn(true);

        // when
        categoryService.addCategory(categoryRequest);

        // then
        List<CategoryDto> categoryList = categoryService.getCategoryListByUser("test1");
        for (CategoryDto categoryDto : categoryList) {
            System.out.println("categoryDto = " + categoryDto);
        }
    }

    private ComCategory getComCategory(CategoryRequest request) {

        ComCategory comCategory = comCategoryRepository
                .findByNameAndEventType(request.getName(), request.getEventType())
                .orElseGet(() -> ComCategory.createCategory(request));
        comCategoryRepository.save(comCategory);

        return comCategory;
    }

    @Test
    //@Ignore
    public void updateCategoryTest () throws Exception {

        UserRequest request = new UserRequest();

        request.setId("test1");
        request.setPassword("password1");
        request.setName("Donggun");
        request.setEmail("chungdk1993@naver.com");
        request.setBirthDate(Year.of(1993).atMonth(11).atDay(17).atTime(14,59));

        userService.addUser(request);

        CategoryRequest categoryRequest = new CategoryRequest();

        categoryRequest.setUserId("test1");
        categoryRequest.setName("chicken");
        categoryRequest.setEventType(EventType.EXPENDITURE);
        categoryRequest.setUseYn(true);

        // when
        categoryService.addCategory(categoryRequest);

        // given
        List<CategoryDto> categoryList = categoryService.getCategoryListByUser("test1");
        CategoryDto category = categoryService.getCategory(categoryList.get(0).getSeq());
        CategoryRequest res = new CategoryRequest();
        res.setName("Pizza");

        // when
        // System.out.println("category = " + category);
        CategoryDto updateCategory = categoryService.updateCategory(category.getSeq(), res);
        category = categoryService.getCategory(updateCategory.getSeq());
        // System.out.println("category = " + category);

        // then

    }

    @Test
    @Rollback(value = false)
    public void deleteCategoryTest () throws Exception {
        // given
        List<CategoryDto> categoryList = categoryService.getCategoryListByUser("test1");
        // categoryService.deleteCategory(categoryList.get(0).getSeq());
        // when

        // then
    }

    /**
     * PK가 어느 타이밍에 생기는 지 궁금해서 해봄
     * @throws Exception
     */
    @Test
    public void pkTest () throws Exception {

        Category category = Category.createCategory(new User(), new ComCategory());
        System.out.println("BEFORE = " + category);

        categoryRepository.addCategory(category);
        System.out.println("AFTER = " + category);
    }
}