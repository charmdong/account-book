package com.accountbook.domain.repository;

import com.accountbook.domain.entity.UserCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserCategoryRepository {

    private final EntityManager em;

    public List<UserCategory> getCategoryListByUserId(String userId) {
        return em.createQuery(
                "select uc from UserCategory uc where uc.user =: userId", UserCategory.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public UserCategory getCategory(Long seq) {
        return em.find(UserCategory.class, seq);
    }

    public void addUserCategory(UserCategory userCategory) {
        em.persist(userCategory);
    }

    public void deleteUserCategory(UserCategory userCategory) {
        em.remove(userCategory);
    }
}
