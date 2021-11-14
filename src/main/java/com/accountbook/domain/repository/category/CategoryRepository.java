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

    public List<Category> getUserCategoryList (String userId) {
        return em.createQuery(
                "select c from Category c where c.user =: userId", Category.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public Category getCategory (Long seq) {

        return em.find(Category.class, seq);
    }

    public void addCategory (Category category) {

        em.persist(category);
    }

    public void deleteCategory (Category category) {

        em.remove(category);
    }
}
