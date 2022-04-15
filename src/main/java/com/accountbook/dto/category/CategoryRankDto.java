package com.accountbook.dto.category;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.accountbook.domain.entity.Category;
import com.accountbook.domain.entity.EcoEvent;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryRankDto implements Comparable<CategoryRankDto> {
    private String name;
    private long total;

    // 카테고리별 사용금액 순위 조회
    public static List<CategoryRankDto> rank(List<Category> categoryList) {
        List<CategoryRankDto> result = new ArrayList<>();

        // 카테고리별 금액 합산
        for (Category category : categoryList) {

            var ecoEventList = category.getEcoEventList();
            long eventTotal = 0;

            for (EcoEvent event : ecoEventList) {
                eventTotal += event.getAmount();
            }
            
            var target = CategoryRankDto.builder().name(category.getName()).total(eventTotal).build();
            result.add(target);
        }

        // 카테고리별 사용금액 내림차순 정렬
        Collections.sort(result);

        return result;
    }

    @Override
    public int compareTo(CategoryRankDto input) {
        if (this.total < input.total) {
            return 1;
        }
        return -1;
    }
}
