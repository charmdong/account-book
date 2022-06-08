package com.accountbook.dto.user;

import com.accountbook.domain.enums.DisplayOption;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateSettingRequest {

    private Integer initDay;
    private DisplayOption displayOption;
}
