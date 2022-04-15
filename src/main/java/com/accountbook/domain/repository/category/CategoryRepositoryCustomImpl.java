package com.accountbook.domain.repository.category;

import com.accountbook.domain.entity.Category;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class CategoryRepositoryCustomImpl extends QuerydslRepositorySupport implements CategoryRepositoryCustom {

    public CategoryRepositoryCustomImpl() {
        super(Category.class);
    }

}
