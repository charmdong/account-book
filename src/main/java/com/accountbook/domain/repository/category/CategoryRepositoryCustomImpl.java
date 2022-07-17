package com.accountbook.domain.repository.category;

import java.util.List;

import com.accountbook.domain.entity.Category;
import com.accountbook.domain.entity.QCategory;
import com.accountbook.domain.entity.QEcoEvent;
import com.accountbook.domain.entity.QUser;

import com.accountbook.domain.enums.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;

public class CategoryRepositoryCustomImpl extends QuerydslRepositorySupport implements CategoryRepositoryCustom {

    @Autowired
    private final EntityManager em;

    public CategoryRepositoryCustomImpl(EntityManager em) {
        super(Category.class);
        this.em = em;
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

    @Override
    public void saveCategory(Category category){ em.persist(category);}

    @Override
    public List<Category> findByNameAndEventType(String name, EventType eventType) {
        QCategory category = QCategory.category;

        return from(category)
                .where(category.name.eq(name))
                .where(category.eventType.eq(eventType))
                .fetch();
    }
}
