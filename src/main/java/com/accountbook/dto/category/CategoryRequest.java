package com.accountbook.dto.category;

import com.accountbook.domain.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * CategoryRequest
 *
 * @author donggun
 * @since 2021/11/15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest {

    @NotEmpty
    private String userId;

    @NotEmpty
    private String name;

    @NotEmpty
    private EventType eventType;

    @NotEmpty
    private Boolean useYn;

}
