package com.accountbook.api;

import com.accountbook.dto.Budget.BudgetDto;
import com.accountbook.dto.Budget.BudgetRequest;
import com.accountbook.dto.asset.ApiResponse;
import com.accountbook.service.BudgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ApiResponse getAllBudget(){
        List<BudgetDto> budgetDtoList = budgetService.getAllBudget();
        ApiResponse apiResponse = new ApiResponse(budgetDtoList, HttpStatus.OK, "SUCCESS");
        return apiResponse;
    }
    //예산 상세 조회
    @GetMapping("/{budgetSeq}")
    public ApiResponse getOneBudget(@PathVariable long budgetSeq){
        BudgetDto buegetdto = budgetService.getOneBudget(budgetSeq);
        ApiResponse apiResponse = new ApiResponse(buegetdto, HttpStatus.OK, "SUCCESS");
        return apiResponse;
    }
    //예산 등록
    @PostMapping("/")
    public ApiResponse create(@RequestBody @Valid BudgetRequest budgetRequest){
        Long buegetSeq = budgetService.enrollBudget(budgetRequest);
        ApiResponse apiResponse = new ApiResponse(buegetSeq, HttpStatus.OK, "SUCCESS");
        return apiResponse;
    }
    //예산 수정
    @PatchMapping("/{budgetSeq}")
    public ApiResponse update(@RequestBody @Valid BudgetRequest budgetRequest,@PathVariable long budgetSeq){
        BudgetDto budgetDto = budgetService.updateBudget(budgetRequest, budgetSeq);
        ApiResponse apiResponse = new ApiResponse(budgetDto, HttpStatus.OK, "SUCCESS");
        return apiResponse;
    }
    //예산 삭제
    @DeleteMapping("/{budgetSeq}")
    public ApiResponse delete(@PathVariable long budgetSeq){
        Boolean result = budgetService.deleteBudget(budgetSeq);
        ApiResponse apiResponse = new ApiResponse(result, HttpStatus.OK, "SUCCESS");
        return apiResponse;
    }
}
