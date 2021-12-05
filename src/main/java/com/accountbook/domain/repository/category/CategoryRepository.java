package com.accountbook.domain.repository.category;

import com.accountbook.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryRepositoryCustom {

    Category findBySeq(Long seq);

    void deleteBySeq(Long seq);
}
