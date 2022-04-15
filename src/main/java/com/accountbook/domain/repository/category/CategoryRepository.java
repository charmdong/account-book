package com.accountbook.domain.repository.category;

import java.util.Optional;

import com.accountbook.domain.entity.Category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryRepositoryCustom {

    Optional<Category> findBySeq(Long seq);
}
