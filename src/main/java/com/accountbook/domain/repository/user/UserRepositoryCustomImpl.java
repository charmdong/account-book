package com.accountbook.domain.repository.user;

import com.accountbook.domain.entity.QUser;
import com.accountbook.domain.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static com.accountbook.domain.entity.QCustomSetting.customSetting;
import static com.accountbook.domain.entity.QUser.user;

@Slf4j
@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    @Override
    public void addUser(User user) {
        em.persist(user);
    }

    @Override
    public List<User> findAllUser() {

        return queryFactory.selectFrom(user)
                .innerJoin(user.setting, customSetting).fetchJoin()
                .fetch();
    }

    @Override
    public Boolean existById (String userId) {

        User findUser = queryFactory.selectFrom(user)
                                    .innerJoin(user.setting, customSetting).fetchJoin()
                                    .where(user.id.eq(userId))
                                    .fetchFirst();

        return findUser != null;
    }

    @Override
    public Boolean existByEmail (String email) {

        User findUser = queryFactory.selectFrom(QUser.user)
                                    .innerJoin(user.setting, customSetting).fetchJoin()
                                    .where(QUser.user.email.eq(email))
                                    .fetchFirst();

        return findUser != null;
    }

    @Override
    public long updateExpireDateByToken(String token, LocalDateTime expireDate) {

        return queryFactory.update(user)
                .set(user.expireDate, expireDate)
                .where(user.token.eq(token))
                .execute();
    }
}
