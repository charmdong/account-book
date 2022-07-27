package com.accountbook.domain.repository.category;

import java.util.List;

import com.accountbook.domain.entity.Category;
import com.accountbook.domain.enums.EventType;

public interface CategoryRepositoryCustom {

    /**
     * category 조회 by userId
     * 
     * @param userId
     * @return
     */
    public List<Category> findByUserId(String userId);

    public void saveCategory(Category category);

    public List<Category> findByNameAndEventType(String name, EventType eventType);
}
