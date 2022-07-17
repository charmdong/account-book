package com.accountbook.dto.category;

import com.accountbook.domain.entity.Category;
import com.accountbook.domain.enums.EventType;
import com.accountbook.dto.EcoEvent.EcoEventDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ToString(exclude = {"ecoEventList"})
@Setter
@Getter
@RequiredArgsConstructor
public class CategoryDto {

    private Long seq;
    private String name;
    private EventType eventType;
    private long defaultPrice;
    private String useYn;
    private List<EcoEventDto> ecoEventList = new ArrayList<>();

    public CategoryDto(Category category) {
        this.seq = category.getSeq();
        this.name = category.getName();
        this.eventType = category.getEventType();
        this.defaultPrice = category.getDefaultPrice();
        this.useYn = category.getUseYn();
        this.ecoEventList = category.getEcoEventList().stream().map(EcoEventDto::new).collect(Collectors.toList());
    }
}
