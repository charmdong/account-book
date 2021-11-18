package com.accountbook.domain.repository;

import java.util.List;
import java.util.Optional;

import com.accountbook.domain.entity.Asset;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {
    
    // 자산 전체 조회
    // List<Asset> findByUserId(String userId);

    // 자산 상세 조회
    Optional<Asset> findById(long assetSeq);

    // 자산 삭제
    void deleteById(long assetSeq);
}
