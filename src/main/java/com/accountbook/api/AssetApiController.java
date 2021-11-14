package com.accountbook.api;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.accountbook.dto.AssetDto;
import com.accountbook.dto.AssetRequest;
import com.accountbook.service.AssetService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/asset")
public class AssetApiController {
    private final AssetService assetService;
    private final HttpSession session;

    // 자산 전체 조회
    @GetMapping("")
    public List<AssetDto> getAssetList(@RequestParam String param) {
        String userId = session.getAttribute("userId").toString();
        return assetService.getAssetList(userId);
    }

    // 자산 상세 조회
    @GetMapping("/{assetSeq}")
    public AssetDto getAssetDetail(@PathVariable("assetSeq") long assetSeq) {
        return assetService.getAsset(assetSeq);
    }

    // 자산 등록
    @PostMapping("")
    public void postMethodName(@RequestBody @Valid AssetRequest assetRequest) {
        assetService.registAsset(assetRequest);
    }

    // 자산 수정
    @PutMapping("/{assetSeq}")
    public void updateAsset(@PathVariable("assetSeq") long assetSeq, @RequestBody @Valid AssetRequest assetRequest) {
        assetService.updateAseet(assetSeq, assetRequest);
    }

    // 자산 삭제
    @DeleteMapping("/{assetSeq}")
    public void removeAsset(@PathVariable("assetSeq") long assetSeq) {
        assetService.removeAsset(assetSeq);
    }

}
