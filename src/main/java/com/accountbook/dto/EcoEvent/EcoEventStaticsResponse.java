package com.accountbook.dto.EcoEvent;

import com.accountbook.dto.category.CategoryDto;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class EcoEventStaticsResponse {

    List<CategoryDto> categoryDtoList;

    List<String> thisMonthExpenditureInfos;

    List<String> moMExpenditureInfos;

    Map<Integer,Long> inTimeExpenseAmountMap;
}
