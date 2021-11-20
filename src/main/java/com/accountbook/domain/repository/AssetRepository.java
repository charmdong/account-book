package com.accountbook.domain.repository;

import java.util.Optional;

import com.accountbook.domain.entity.Asset;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long>, AssetRepositoryCustom {
    
    // 자산 상세 조회
    Optional<Asset> findById(long assetSeq);

    // 자산 삭제
    void deleteById(long assetSeq);
}
