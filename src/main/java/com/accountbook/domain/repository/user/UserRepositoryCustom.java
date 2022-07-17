package com.accountbook.domain.repository.user;

import com.accountbook.domain.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public interface UserRepositoryCustom {

    void addUser (User user);
    List<User> findAllUser ();
    long updateExpireDateByToken(String token, LocalDateTime expireDate);
}