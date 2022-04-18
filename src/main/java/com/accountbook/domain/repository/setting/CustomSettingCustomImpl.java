package com.accountbook.domain.repository.setting;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
public class CustomSettingCustomImpl implements CustomSettingCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

}
