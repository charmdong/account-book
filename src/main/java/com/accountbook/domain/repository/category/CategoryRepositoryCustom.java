package com.accountbook.domain.repository.category;

import java.util.List;

import com.accountbook.domain.entity.Category;

public interface CategoryRepositoryCustom {

    /**
     * category 조회 by userId
     * 
     * @param userId
     * @return
     */
    public List<Category> findByUserId(String userId);

}
