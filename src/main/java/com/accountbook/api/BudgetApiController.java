package com.accountbook.api;

import com.accountbook.dto.Budget.BudgetRequest;
import com.accountbook.dto.asset.ApiResponse;
import com.accountbook.service.BudgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/budget")
@RequiredArgsConstructor
public class BudgetApiController {
    private final BudgetService budgetService;

    //예산 전체 조회
    @GetMapping("/")
    public ApiResponse getAllBudget(){
        return new ApiResponse(budgetService.getAllBudget(), HttpStatus.OK, "SUCCESS");
    }
    //예산 상세 조회
    @GetMapping("/{budgetSeq}")
    public ApiResponse getOneBudget(@PathVariable long budgetSeq) throws Exception {
        return new ApiResponse(budgetService.getOneBudget(budgetSeq), HttpStatus.OK, "SUCCESS");
    }
    //예산 등록
    @PostMapping("/")
    public ApiResponse create(@RequestBody @Valid BudgetRequest budgetRequest) throws Exception {
        return new ApiResponse(budgetService.enrollBudget(budgetRequest), HttpStatus.OK, "SUCCESS");
    }
    //예산 수정
    @PatchMapping("/{budgetSeq}")
    public ApiResponse update(@RequestBody @Valid BudgetRequest budgetRequest,@PathVariable long budgetSeq) throws Exception {
        return new ApiResponse(budgetService.updateBudget(budgetRequest, budgetSeq), HttpStatus.OK, "SUCCESS");
    }
    //예산 삭제
    @DeleteMapping("/{budgetSeq}")
    public ApiResponse delete(@PathVariable long budgetSeq) throws Exception {
        return new ApiResponse(budgetService.deleteBudget(budgetSeq), HttpStatus.OK, "SUCCESS");
    }
}
