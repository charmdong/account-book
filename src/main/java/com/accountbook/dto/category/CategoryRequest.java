package com.accountbook.dto.category;

import com.accountbook.domain.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest {

    private String name;
    private EventType eventType;
    private Long defaultPrice;
    private String useYn;
}
