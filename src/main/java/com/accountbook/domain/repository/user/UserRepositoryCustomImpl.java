package com.accountbook.domain.repository.user;

import com.accountbook.domain.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
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
