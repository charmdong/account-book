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
    private CustomSettingDto settingDto;

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.birthDate = user.getBirthDate();
        this.settingDto = new CustomSettingDto(user.getSetting());
    }
}
