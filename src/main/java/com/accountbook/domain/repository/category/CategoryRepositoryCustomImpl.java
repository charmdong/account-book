package com.accountbook.domain.repository.category;

import java.util.List;

import com.accountbook.domain.entity.Category;
import com.accountbook.domain.entity.QCategory;
import com.accountbook.domain.entity.QEcoEvent;
import com.accountbook.domain.entity.QUser;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class CategoryRepositoryCustomImpl extends QuerydslRepositorySupport implements CategoryRepositoryCustom {

    public CategoryRepositoryCustomImpl() {
        super(Category.class);
    }

    @Override
    public List<Category> findByUserId(String userId) {
        QCategory category = QCategory.category;
        QEcoEvent ecoEvent = QEcoEvent.ecoEvent;
        QUser user = QUser.user;

        return from(category)
                .innerJoin(category.ecoEventList, ecoEvent).fetchJoin()
                .innerJoin(ecoEvent.user, user).fetchJoin()
                .where(user.id.eq(userId))
                .fetch();

    }

}
