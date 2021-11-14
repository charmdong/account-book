package com.accountbook.service;

import java.util.ArrayList;
import java.util.List;

import com.accountbook.domain.entity.Asset;
import com.accountbook.domain.repository.AssetRepository;
import com.accountbook.dto.AssetDto;
import com.accountbook.dto.AssetRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AssetService {
    private final AssetRepository assetRepository;

    // 자산 전체 조회
    public List<AssetDto> getAssetList(String userId) {
        List<AssetDto> ret = AssetDto.convertAssetList(assetRepository.findByUserId(userId));

        return ret;
    }

    // 자산 상세 조회
    public AssetDto getAsset(long assetSeq) {
        AssetDto ret = new AssetDto(assetRepository.findByAssetSeq(assetSeq).orElseThrow());
        return ret;
    }

    // 자산 등록
    @Transactional
    public void registAsset(AssetRequest assetRequest) {
        Asset asset = Asset.createAsset(assetRequest);
        assetRepository.addAsset(asset);
    }

    // 자산 수정
    @Transactional
    public void updateAseet(long assetSeq, AssetRequest assetRequest) {
        Asset asset = assetRepository.findByAssetSeq(assetSeq).orElseThrow();
        asset.changeAsset(assetRequest);
    }

    // 자산 삭제
    @Transactional
    public void removeAsset(long assetSeq) {
        assetRepository.deleteByAssetSeq(assetSeq);
    }
}
