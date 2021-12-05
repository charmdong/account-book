package com.accountbook.service;

import java.util.List;

import com.accountbook.domain.entity.Asset;
import com.accountbook.domain.repository.asset.AssetRepository;
import com.accountbook.dto.asset.AssetDto;
import com.accountbook.dto.asset.AssetRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
        AssetDto ret = new AssetDto(assetRepository.findById(assetSeq).orElseThrow());
        return ret;
    }

    // 자산 등록
    @Transactional(readOnly = false)
    public Boolean registAsset(AssetRequest assetRequest) {
        Boolean result = Boolean.FALSE;
        try {
            Asset asset = Asset.createAsset(assetRequest);
            assetRepository.save(asset);
            result = Boolean.TRUE;
        } catch (Exception e) {
            log.error("Failed to regist asset : {}", e);
        }
        return result;
    }

    // 자산 수정
    @Transactional(readOnly = false)
    public AssetDto updateAseet(long assetSeq, AssetRequest assetRequest) {
        Asset asset = assetRepository.findById(assetSeq).orElseThrow();
        asset.changeAsset(assetRequest);
        assetRepository.flush();

        // 업데이트 확인용 조회
        AssetDto assetDto = new AssetDto(assetRepository.findById(assetSeq).orElseThrow());
        return assetDto;
    }

    // 자산 삭제
    @Transactional(readOnly = false)
    public Boolean removeAsset(long assetSeq) {
        Boolean result = Boolean.FALSE;
        try {
            assetRepository.deleteById(assetSeq);
            result = Boolean.TRUE;
        } catch (Exception e) {
            log.error("Failed to delete asset : {}", e);
        }
        return result;
    }
}
