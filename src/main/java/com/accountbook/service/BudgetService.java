package com.accountbook.service;

import com.accountbook.domain.entity.Budget;
import com.accountbook.domain.entity.Category;
import com.accountbook.domain.entity.User;
import com.accountbook.domain.repository.budget.BudgetRepository;
import com.accountbook.domain.repository.category.CategoryRepository;
import com.accountbook.domain.repository.user.UserRepository;
import com.accountbook.dto.Budget.BudgetDto;
import com.accountbook.dto.Budget.BudgetRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BudgetService {
    private final BudgetRepository budgetRepository;

    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;

    //예산 전체 조회
    @Transactional(readOnly = true)
    public List<BudgetDto> getAllBudget(){
        return budgetRepository.findAll().stream().map(BudgetDto::new).collect(Collectors.toList());
    }

    //예산 상세 조회
    @Transactional(readOnly = true)
    public BudgetDto getOneBudget(Long budgetSeq){
        return new BudgetDto(budgetRepository.findBySeq(budgetSeq).orElseThrow(() -> new NoSuchElementException()));
    }

    //예산 조회 by User
    @Transactional(readOnly = true)
    public List<BudgetDto> getBudgetByUser(String userId){
        return budgetRepository.findByUserId(userId).stream().map(BudgetDto::new).collect(Collectors.toList());
    }

    //예산 등록
    public void enrollBudget(BudgetRequest budgetRequest){
        User user = userRepository.findById(budgetRequest.getUserId()).orElseThrow(() -> new NoSuchElementException());
        Category category = categoryRepository.getCategory(budgetRequest.getCategorySeq());
        Budget budget = Budget.createBudget(budgetRequest, category, user);

        budgetRepository.saveBudget(budget);
    }

    //예산 수정
    public void updateBudget(BudgetRequest budgetRequest, Long budgetSeq){
        Budget budget = budgetRepository.findBySeq(budgetSeq).orElseThrow(() -> new NoSuchElementException());
        Category category = categoryRepository.getCategory(budgetRequest.getCategorySeq());
        budget.changeBudget(budgetRequest, category);
    }

    //예산 삭제
    public void deleteBudget(Long budgetSeq){
        budgetRepository.deleteById(budgetSeq);
    }
}
