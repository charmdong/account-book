package com.accountbook.domain.entity;

import com.accountbook.domain.enums.DisplayOption;
import com.accountbook.dto.user.UpdateSettingRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"user"})
public class CustomSetting {

    @Id
    @Column(name = "USER_ID")
    private String id;

    @MapsId
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    private User user;

    private int initDay; // 조회 기준 시작일

    @Enumerated(EnumType.STRING)
    private DisplayOption option; // 메인 화면 출력 타입

    // 생성자 메서드
    public static CustomSetting createCustomSetting () {

        CustomSetting setting = new CustomSetting();

        setting.initDay = 1;
        setting.option = DisplayOption.AMOUNT;

        return setting;
    }

    // 비즈니스 메서드
    public void setUser(User user) {
        this.user = user;
    }

    public void updateSetting(UpdateSettingRequest request) {

        if (request.getInitDay() != null) {
            this.initDay = request.getInitDay();
        }

        if (request.getOption() != null) {
            this.option = request.getOption();
        }
    }
}
