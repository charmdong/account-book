package com.accountbook.domain.repository.user;

import com.accountbook.domain.entity.User;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    private final EntityManager em;

    @Override
    public void addUser(User user) {
        em.persist(user);
    }
}
