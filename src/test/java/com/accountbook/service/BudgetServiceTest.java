package com.accountbook.service;

import com.accountbook.domain.entity.Category;
import com.accountbook.domain.entity.User;
import com.accountbook.domain.enums.EventType;
import com.accountbook.domain.enums.PeriodType;
import com.accountbook.dto.Budget.BudgetDto;
import com.accountbook.dto.Budget.BudgetRequest;
import com.accountbook.dto.user.UserRequest;
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
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
class BudgetServiceTest {

    @Autowired
    private BudgetService budgetService;

    @Test
    void enroll_Budget() {
        //given
        User testUser = getUser();
        Category testCategory = getCategory();
        BudgetRequest budgetRequest = new BudgetRequest(testCategory,PeriodType.MONTHLY,100000L,testUser);

        //when
        budgetService.enrollBudget(budgetRequest);

        //then
        List<BudgetDto> budgetDtoList = budgetService.getBudgetByUser(testUser.getId());
        assertEquals(100000L,budgetDtoList.get(budgetDtoList.size()-1).getAmount());
    }


    @Test
    public void update_Budget() {
        //given
        List<BudgetDto> budgetDtoList = budgetService.getAllBudget();
        if(budgetDtoList.size() == 0)  return;
        BudgetDto budgetDto = budgetDtoList.get(budgetDtoList.size()-1);

        User testUser = getUser();
        Category testCategory = getCategory();
        BudgetRequest budgetRequest = new BudgetRequest(testCategory,PeriodType.ANNUAL,300000L,testUser);

        //when
        budgetService.updateBudget(budgetRequest,budgetDto.getSeq());

        //then
        assertEquals(300000L,budgetService.getOneBudget(budgetDto.getSeq()).getAmount());
        assertEquals(PeriodType.ANNUAL,budgetService.getOneBudget(budgetDto.getSeq()).getPeriodType());
    }

    @Test
    public void delete_Budget() {
        //given
        List<BudgetDto> budgetDtoList = budgetService.getAllBudget();
        if(budgetDtoList.size() == 0)  return;
        BudgetDto budgetDtoDeleteBefore = budgetDtoList.get(budgetDtoList.size()-1);

        //when
        budgetService.deleteBudget(budgetDtoDeleteBefore.getSeq());

        //then
        BudgetDto budgetdtoDeleteAfter = budgetService.getOneBudget(budgetDtoDeleteBefore.getSeq());
        assertTrue(budgetdtoDeleteAfter == null);
    }

    public User getUser(){
        UserRequest request = new UserRequest();

        request.setId("rlfehd1");
        request.setPassword("ghdrlfehed123!");
        request.setName("홍길동");
        request.setEmail("ghdrlfehd@gmail.com");
        request.setBirthDate(Year.of(2000).atMonth(12).atDay(31).atTime(14,03));

        return User.createUser(request);
    }

    private Category getCategory() {
        Category categoryRequest = new Category();

        categoryRequest.setUserId("rlfehd1");
        categoryRequest.setName("chicken");
        categoryRequest.setEventType(EventType.EXPENDITURE);
        categoryRequest.setUseYn(true);

        //categoryService.addUserCategory(categoryRequest);
        return Category.createUserCategory(categoryRequest);
    }
}