package com.accountbook.service;

import com.accountbook.domain.enums.EventType;
import com.accountbook.domain.enums.PeriodType;
import com.accountbook.dto.Budget.BudgetDto;
import com.accountbook.dto.Budget.BudgetRequest;
import com.accountbook.dto.category.CategoryDto;
import com.accountbook.dto.category.CategoryRequest;
import com.accountbook.dto.user.UserRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.time.Year;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
class BudgetServiceTest {

    @Autowired
    private BudgetService budgetService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    //예산 등록
    @Test
    void enroll_Budget() {
        //given
        BudgetRequest budgetRequest = createBudgetRequest();

        //when
        budgetService.enrollBudget(budgetRequest);

        //then
        List<BudgetDto> budgetDtoList = budgetService.getBudgetByUser(budgetRequest.getUserId());
        assertEquals(budgetRequest.getAmount(),budgetDtoList.get(budgetDtoList.size()-1).getAmount());
    }


    //예산 수정
    @Test
    public void update_Budget() {
        //given
        //1. 예산 등록
        BudgetRequest budgetRequest = createBudgetRequest();
        budgetService.enrollBudget(budgetRequest);
        Long budgetSeq = budgetService.getBudgetByUser(budgetRequest.getUserId()).get(0).getSeq();

        //2. 수정 Dto 생성
        BudgetRequest updateBudgetRequest = new BudgetRequest(budgetRequest.getCategorySeq(),PeriodType.ANNUAL,300000L,budgetRequest.getUserId());

        //when
        budgetService.updateBudget(updateBudgetRequest,budgetSeq);

        //then
        assertFalse(budgetRequest.getAmount() == budgetService.getOneBudget(budgetSeq).getAmount());
        assertTrue(updateBudgetRequest.getAmount() == budgetService.getOneBudget(budgetSeq).getAmount());
        assertEquals(updateBudgetRequest.getPeriodType(),budgetService.getOneBudget(budgetSeq).getPeriodType());
    }

    //예산 삭제
    @Test
    public void delete_Budget() {
        //given
        BudgetRequest budgetRequest = createBudgetRequest();
        budgetService.enrollBudget(budgetRequest);
        Long budgetSeq = budgetService.getBudgetByUser(budgetRequest.getUserId()).get(0).getSeq();

        //when
        budgetService.deleteBudget(budgetSeq);

        //then
        assertTrue(budgetService.getOneBudget(budgetSeq) == null);
    }

    //테스트용 budgetRequest 생성
    private BudgetRequest createBudgetRequest() {
        String testUserId = getUser();
        Long testCategorySeq = getCategory(testUserId);
        BudgetRequest budgetRequest = new BudgetRequest(testCategorySeq, PeriodType.MONTHLY, 100000L, testUserId);

        return budgetRequest;
    }

    //테스트용 User 생성
    public String getUser(){
        UserRequest request = new UserRequest();
        String userId = "rlfehd1";

        request.setId(userId);
        request.setPassword("ghdrlfehed123!");
        request.setName("홍길동");
        request.setEmail("ghdrlfehd@gmail.com");
        request.setBirthDate(Year.of(2000).atMonth(12).atDay(31).atTime(14,03));

        userService.addUser(request);

        return userId;
    }

    //테스트용 Category 생성
    private Long getCategory(String userId) {

        CategoryRequest categoryRequest = new CategoryRequest();

        categoryRequest.setUserId("rlfehd1");
        categoryRequest.setName("chicken");
        categoryRequest.setEventType(EventType.EXPENDITURE);
        categoryRequest.setUseYn(true);

        categoryService.addUserCategory(categoryRequest);
        List<CategoryDto> categoryDtoList = categoryService.getUserCategoryList(userId);

        return categoryDtoList.get(categoryDtoList.size()-1).getSeq();
    }

}