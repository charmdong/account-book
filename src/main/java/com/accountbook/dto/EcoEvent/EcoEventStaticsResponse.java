package com.accountbook.dto.EcoEvent;

import com.accountbook.dto.category.CategoryListDto;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class EcoEventStaticsResponse {

    List<CategoryListDto> categoryDtoList;

    List<String> moMExpenditureInfos;

    List<String> thisMonthExpenditureInfos;

    List<String> thisMonthExpenditureInfoPercent;

    Map<Integer,Long> inTimeExpenseAmountMap;
}
