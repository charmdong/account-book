package com.accountbook.dto.user;

import com.accountbook.domain.entity.CustomSetting;
import com.accountbook.domain.enums.DisplayOption;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * CustomSettingDto
 *
 * @author donggun
 * @since 2022/04/14
 */
@Data
@NoArgsConstructor
@ToString
public class CustomSettingDto {

    private int initDay; // 조회 기준 시작일
    private DisplayOption option; // 메인 화면 출력 타입

    public CustomSettingDto(CustomSetting setting) {
        this.initDay = setting.getInitDay();
        this.option = setting.getOption();
    }
}
