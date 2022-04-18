package com.accountbook.domain.repository.user;

import com.accountbook.domain.entity.User;

import java.time.LocalDateTime;

public interface UserRepositoryCustom {

    void addUser (User user);
    long updateExpireDateByToken(String token, LocalDateTime expireDate);
}