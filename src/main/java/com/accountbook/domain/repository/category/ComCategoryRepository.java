package com.accountbook.domain.repository.category;

import com.accountbook.domain.enums.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ComCategoryRepository extends JpaRepository<ComCategory, Long> {

    /**
     * 카테고리 공통 코드 정보 조회
     *
     * @param name
     * @param eventType
     * @return
     */
    Optional<ComCategory> findByNameAndEventType(String name, EventType eventType);
}
