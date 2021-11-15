package com.accountbook.domain.repository.category;

import com.accountbook.domain.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

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

    /**
     * 사용자 카테고리 목록 조회
     *
     * @param userId
     * @return 사용자 카테고리 목록
     */
    public List<Category> getUserCategoryList (String userId) {
        return em.createQuery(
                "select c from Category c where c.user =: userId", Category.class)
                .setParameter("userId", userId)
                .getResultList();
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
     * @param category
     */
    public void deleteCategory (Category category) {

        em.remove(category);
    }
}
