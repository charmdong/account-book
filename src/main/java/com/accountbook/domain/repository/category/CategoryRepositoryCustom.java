package com.accountbook.domain.repository.category;

import com.accountbook.domain.entity.Category;
import com.accountbook.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface CategoryRepositoryCustom {

    Optional<List<Category>> getCategoryListByUser(User user);

    void addCategory(Category category);
}
