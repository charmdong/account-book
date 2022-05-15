package com.accountbook.dto.user;

import com.accountbook.domain.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserDto {

    private String id;
    private String name;
    private String email;
    private LocalDateTime birthDate;
    
    // 지난 달 수입, 지출 금액
    private Long prevIncome;
    private Long prevExpenditure;

    private CustomSettingDto settingDto;

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.birthDate = user.getBirthDate();
        this.prevIncome = user.getPrevIncome();
        this.prevExpenditure = user.getPrevExpenditure();
        this.settingDto = new CustomSettingDto(user.getSetting());
    }
}
