package com.accountbook.domain.repository.category;

import com.accountbook.domain.entity.Category;
import com.accountbook.domain.entity.QCategory;
import com.accountbook.domain.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

/**
 * CategoryRepository
 *
 * @author donggun
 * @since 2021/11/15
 */
@Repository
@RequiredArgsConstructor
public class CategoryRepository {

    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 사용자 카테고리 목록 조회
     *
     * @param user
     * @return 사용자 카테고리 목록
     */
    public Optional<List<Category>> getCategoryListByUser (User user) {

        QCategory category = QCategory.category;

        List<Category> categoryList = jpaQueryFactory
                .selectFrom(category)
                .where(category.user.eq(user))
                .fetch();

        return Optional.ofNullable(categoryList);
    }

    /**
     * 사용자 카테고리 상세 조회
     *
     * @param seq
     * @return 사용자 카테고리 상세 정보
     */
    public Category getCategory (Long seq) {

        return em.find(Category.class, seq);
    }

    /**
     * 사용자 카테고리 등록
     *
     * @param category
     */
    public void addCategory (Category category) {

        em.persist(category);
    }

    /**
     * 사용자 카테고리 삭제
     *
     * @param seq
     */
    public void deleteCategory (Long seq) {

        em.remove(getCategory(seq));
    }
}
