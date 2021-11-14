package com.accountbook.domain.repository.category;

import com.accountbook.domain.entity.ComCategory;
import com.accountbook.domain.enums.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComCategoryRepository extends JpaRepository<ComCategory, Long> {

    ComCategory findByNameAndEventType(String name, EventType eventType);
}
