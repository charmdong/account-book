package com.accountbook.dto.user;

import com.accountbook.domain.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCategoryDto {

    private String name;
    private EventType eventType;

}
