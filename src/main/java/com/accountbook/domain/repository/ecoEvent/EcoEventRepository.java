package com.accountbook.domain.repository.ecoEvent;

import com.accountbook.domain.entity.EcoEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EcoEventRepository extends JpaRepository<EcoEvent, Long> ,EcoEventRepositoryCustom {

    //금웅 이벤트 전체 조회
    List<EcoEvent>findAll();

    //금융 이벤트 상세 조회
    Optional<EcoEvent> findBySeq(Long ecoEventSeq);

    //금융 이벤트  삭제
    void deleteBySeq(Long ecoEventSeq);

    //금융 이벤트 삭제 - 사용자 아이디
    void deleteByUserId(String userId);
}
