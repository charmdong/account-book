package com.accountbook.dto.user;

import com.accountbook.domain.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginInfo {

    private String userId;
    private String name;

    public LoginInfo (User user) {
        this.userId = user.getId();
        this.name = user.getName();
    }
}
