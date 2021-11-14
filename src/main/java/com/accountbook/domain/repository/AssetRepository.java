package com.accountbook.domain.repository;

import java.util.List;
import java.util.Optional;

import com.accountbook.domain.entity.Asset;
import com.accountbook.domain.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository extends JpaRepository<User, Long> {
    
    // 자산 전체 조회
    List<Asset> findByUserId(String userId);

    // 자산 상세 조회
    Optional<Asset> findByAssetSeq(long assetSeq);

    // 자산 등록
    void addAsset(Asset asset);

    // 자산 삭제
    void deleteByAssetSeq(long assetSeq);
}
