package com.accountbook.domain.repository.category;

import com.accountbook.domain.entity.Category;
import com.accountbook.domain.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.accountbook.domain.entity.QCategory.category;

@RequiredArgsConstructor
public class CategoryRepositoryCustomImpl implements CategoryRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<List<Category>> getCategoryListByUser(User user) {

        List<Category> categoryList = queryFactory
                .selectFrom(category)
                .where(category.user.eq(user))
                .fetch();

        return Optional.ofNullable(categoryList);
    }

    @Override
    public void addCategory(Category category) {

        em.persist(category);
    }
}
