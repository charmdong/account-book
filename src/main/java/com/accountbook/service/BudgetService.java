package com.accountbook.service;

import com.accountbook.domain.entity.Budget;
import com.accountbook.domain.repository.BudgetRepository;
import com.accountbook.dto.Budget.BudgetDto;
import com.accountbook.dto.Budget.BudgetRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BudgetService {
    private final BudgetRepository budgetRepository;

    //예산 전체 조회
    @Transactional(readOnly = true)
    public List<BudgetDto> getAllBudget(){
        return budgetRepository.findAll().stream().map(BudgetDto::new).collect(Collectors.toList());
    }

    //예산 상세 조회
    @Transactional(readOnly = true)
    public BudgetDto getOneBudget(Long budgetId){
        return new BudgetDto(budgetRepository.findById(budgetId));
    }

    //예산 등록
    public void enrollBudget(BudgetRequest budgetRequest){
        Budget budget = Budget.createBudget(budgetRequest);
        budgetRepository.save(budget);
    }

    //예산 수정
    public void updateBudget(BudgetRequest budgetRequest, Long budgetSeq){
        Budget budget = budgetRepository.findById(budgetSeq);
        if(budget == null){
            throw new IllegalStateException();
        }
        budget.changeBudget(budgetRequest);
        budgetRepository.save(budget);
    }

    //예산 삭제
    public void deleteBudget(Long budgetId){
        Budget budget = budgetRepository.findById(budgetId);
        if(budget == null){
            throw new IllegalStateException();
        }
        budgetRepository.remove(budget);
    }
}
