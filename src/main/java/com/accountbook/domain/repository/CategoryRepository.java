package com.accountbook.domain.repository;

import com.accountbook.domain.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryRepository {

    private final EntityManager em;

    public List<Category> getCategoryListByUserId(String userId) {
        return em.createQuery(
                "select uc from UserCategory uc where uc.user =: userId", Category.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public Category getCategory(Long seq) {
        return em.find(Category.class, seq);
    }

    public void addUserCategory(Category category) {
        em.persist(category);
    }

    public void deleteUserCategory(Category category) {
        em.remove(category);
    }
}
