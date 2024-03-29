package com.accountbook.dto.user;

import com.accountbook.domain.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {

    private String id;
    private String name;
    private String email;
    private String birthDate;
    
    // 지난 달 수입, 지출 금액
    private Long prevIncome;
    private Long prevExpenditure;

    private CustomSettingDto settingDto;

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();

        if (user.getBirthDate() != null) {
            this.birthDate = user.getBirthDate().toString();
        }

        this.prevIncome = user.getPrevIncome();
        this.prevExpenditure = user.getPrevExpenditure();
        this.settingDto = new CustomSettingDto(user.getSetting());
    }
}
