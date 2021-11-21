package com.accountbook.domain.repository;

import com.accountbook.domain.entity.User;
import com.accountbook.dto.user.UserRequest;

public interface UserRepositoryCustom {

    void addUser(User user);
}
