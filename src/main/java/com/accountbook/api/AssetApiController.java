package com.accountbook.api;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.accountbook.dto.asset.ApiResponse;
import com.accountbook.dto.asset.AssetDto;
import com.accountbook.dto.asset.AssetRequest;
import com.accountbook.service.AssetService;

import org.springframework.http.HttpStatus;
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
    public ApiResponse getAssetList(@RequestParam String param) {
        String userId = session.getAttribute("userId").toString();
        List<AssetDto> data = assetService.getAssetList(userId);

        ApiResponse response = new ApiResponse(data, HttpStatus.OK, "SUCCES");
        return response;
    }

    // 자산 상세 조회
    @GetMapping("/{assetSeq}")
    public ApiResponse getAssetDetail(@PathVariable("assetSeq") long assetSeq) {
        AssetDto data =  assetService.getAsset(assetSeq);
        ApiResponse response = new ApiResponse(data, HttpStatus.OK, "SUCCES");
        return response;
    }

    // 자산 등록
    @PostMapping("")
    public ApiResponse postMethodName(@RequestBody @Valid AssetRequest assetRequest) {
        assetService.registAsset(assetRequest);
        ApiResponse response = new ApiResponse(null, HttpStatus.OK, "SUCCES");
        return response;
    }

    // 자산 수정
    @PutMapping("/{assetSeq}")
    public ApiResponse updateAsset(@PathVariable("assetSeq") long assetSeq, @RequestBody @Valid AssetRequest assetRequest) {
        assetService.updateAseet(assetSeq, assetRequest);
        ApiResponse response = new ApiResponse(null, HttpStatus.OK, "SUCCES");
        return response;
    }

    // 자산 삭제
    @DeleteMapping("/{assetSeq}")
    public ApiResponse removeAsset(@PathVariable("assetSeq") long assetSeq) {
        assetService.removeAsset(assetSeq);
        ApiResponse response = new ApiResponse(null, HttpStatus.OK, "SUCCES");
        return response;
    }

}
