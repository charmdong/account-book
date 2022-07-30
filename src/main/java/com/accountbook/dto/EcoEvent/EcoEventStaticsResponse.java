package com.accountbook.dto.EcoEvent;

import com.accountbook.dto.category.CategoryDto;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class EcoEventStaticsResponse {

    List<CategoryDto> categoryDtoList;

    List<String> moMExpenditureInfos;

    List<String> thisMonthExpenditureInfos;

    List<String> thisMonthExpenditureInfoPercent;

    Map<Integer,Long> inTimeExpenseAmountMap;
}
