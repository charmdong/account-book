package com.accountbook.domain.repository.user;

import com.accountbook.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    private final EntityManager em;

    @Override
    public void addUser(User user) {

        em.persist(user);
    }

    @Override
    @Query(value = "")
    public void updateExpireDate (String uid, LocalDateTime expireDate) {

    }
}
