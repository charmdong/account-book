package com.accountbook.api;

import com.accountbook.dto.Budget.BudgetDto;
import com.accountbook.dto.Budget.BudgetRequest;
import com.accountbook.service.BudgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/budget")
@RequiredArgsConstructor
public class BudgetApiController {
    private final BudgetService budgetService;

    //예산 전체 조회
    @GetMapping("/")
    public List<BudgetDto> getAllBudget(){
        return budgetService.getAllBudget();
    }
    //예산 상세 조회
    @GetMapping("/{budgetSeq}")
    public BudgetDto getOneBudget(@PathVariable long budgetSeq){
        return budgetService.getOneBudget(budgetSeq);
    }
    //예산 등록
    @PostMapping("/")
    public void create(@RequestBody @Valid BudgetRequest budgetRequest){
        budgetService.enrollBudget(budgetRequest);
    }
    //예산 수정
    @PatchMapping("/{budgetSeq}")
    public void update(@RequestBody @Valid BudgetRequest budgetRequest,@PathVariable long budgetSeq){
        budgetService.updateBudget(budgetRequest, budgetSeq);
    }
    //예산 삭제
    @DeleteMapping("/{budgetSeq}")
    public void delete(@PathVariable long budgetSeq){
        budgetService.deleteBudget(budgetSeq);
    }
}
