package com.accountbook.domain.repository;

import com.accountbook.domain.entity.Category;
import com.accountbook.domain.enums.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    public Category findByNameAndEventType(String name, EventType eventType);
}
