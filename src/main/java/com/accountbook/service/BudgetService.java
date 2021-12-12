package com.accountbook.service;

import com.accountbook.domain.entity.Budget;
import com.accountbook.domain.entity.Category;
import com.accountbook.domain.entity.User;
import com.accountbook.domain.repository.budget.BudgetRepository;
import com.accountbook.domain.repository.category.CategoryRepository;
import com.accountbook.domain.repository.user.UserRepository;
import com.accountbook.dto.Budget.BudgetDto;
import com.accountbook.dto.Budget.BudgetRequest;
import com.accountbook.exception.budget.BudgetUpdateException;
import com.accountbook.exception.budget.RelatedCategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
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
    public BudgetDto getOneBudget(Long budgetSeq) throws Exception{
        return new BudgetDto(budgetRepository.findBySeq(budgetSeq).orElseThrow(()-> new NoSuchElementException()));
    }

    //예산 조회 by User
    @Transactional(readOnly = true)
    public List<BudgetDto> getBudgetByUser(String userId) throws Exception{
        return budgetRepository.findByUserId(userId).stream().map(BudgetDto::new).collect(Collectors.toList());
    }

    //예산 등록
    public Long enrollBudget(BudgetRequest budgetRequest) throws Exception{
        User user = userRepository.findById(budgetRequest.getUserId()).orElseThrow(() -> new NoSuchElementException());
        Category category = categoryRepository.findBySeq(budgetRequest.getCategorySeq()).orElseThrow(()-> new RelatedCategoryNotFoundException());
        Budget budget = Budget.createBudget(budgetRequest, category, user);

        budgetRepository.saveBudget(budget);
        return budget.getSeq();
    }

    //예산 수정
    public BudgetDto updateBudget(BudgetRequest budgetRequest, Long budgetSeq) throws Exception{
        Budget budget = budgetRepository.findBySeq(budgetSeq).orElseThrow(() -> new NoSuchElementException());
        Category category = categoryRepository.findBySeq(budgetRequest.getCategorySeq()).orElseThrow(()-> new RelatedCategoryNotFoundException());

        //Dirty checking
        budget.changeBudget(budgetRequest, category);

        //flush 후 조회
        budgetRepository.flush();
        BudgetDto findBudgetDto = new BudgetDto(budgetRepository.findBySeq(budgetSeq).orElseThrow(() -> new BudgetUpdateException()));

        return findBudgetDto;
    }

    //예산 삭제
    public Boolean deleteBudget(Long budgetSeq) throws Exception{
        try {
            budgetRepository.deleteById(budgetSeq);
            budgetRepository.findBySeq(budgetSeq).orElseThrow(()-> new NoSuchElementException());
        }catch (NoSuchElementException e){
            return true;
        }
        return false;
    }
}
