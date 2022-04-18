package com.accountbook.domain.repository.user;

import com.accountbook.domain.entity.QUser;
import com.accountbook.domain.entity.User;
import com.querydsl.core.QueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    @Override
    public void addUser(User user) {
        em.persist(user);
    }

    @Override
    public long updateExpireDateByToken(String token, LocalDateTime expireDate) {
        QUser user = QUser.user;

        return queryFactory.update(user)
                .set(user.expireDate, expireDate)
                .where(user.token.eq(token))
                .execute();
    }
}
