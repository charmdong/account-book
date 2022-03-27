package com.accountbook.api;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.accountbook.common.utils.CookieUtils;
import com.accountbook.dto.asset.AssetDto;
import com.accountbook.dto.asset.AssetRequest;
import com.accountbook.dto.response.ApiResponse;
import com.accountbook.service.AssetService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/asset")
public class AssetApiController {
    private final AssetService assetService;

    // 자산 전체 조회
    @GetMapping("")
    @ApiOperation(value = "자산 목록 조회", notes = " 자산 목록 조회")
    public ApiResponse getAssetList(HttpServletRequest request) throws Exception {
        String userId = CookieUtils.getCookieValueByName(request.getCookies(), "userId");
        List<AssetDto> data = assetService.getAssetList(userId);
        ApiResponse response = new ApiResponse(data, HttpStatus.OK, "SUCCES");
        return response;
    }

    // 자산 상세 조회
    @GetMapping("/{assetSeq}")
    @ApiOperation(value = "자산 상세 조회", notes = "assetSeq을 통한 자산 상세 조회")
    public ApiResponse getAssetDetail(@PathVariable("assetSeq") long assetSeq) throws Exception {
        AssetDto data =  assetService.getAsset(assetSeq);

        ApiResponse response = new ApiResponse(data, HttpStatus.OK, "SUCCES");
        return response;
    }

    // 자산 등록
    @PostMapping("")
    @ApiOperation(value = "자산 등록", notes = "자산 등록")
    public ApiResponse postMethodName(@RequestBody AssetRequest assetRequest) throws Exception {
        Boolean result = assetService.registAsset(assetRequest);
        System.out.println(LocalDateTime.now());

        ApiResponse response = new ApiResponse(result, HttpStatus.OK, "SUCCES");
        return response;
    }

    // 자산 수정
    @PutMapping("/{assetSeq}")
    @ApiOperation(value = "자산 수정", notes = "assetSeq을 통한 자산 수정")
    public ApiResponse updateAsset(@PathVariable("assetSeq") long assetSeq, @RequestBody @Valid AssetRequest assetRequest) throws Exception {
        AssetDto data = assetService.updateAseet(assetSeq, assetRequest);

        ApiResponse response = new ApiResponse(data, HttpStatus.OK, "SUCCES");
        return response;
    }

    // 자산 삭제
    @DeleteMapping("/{assetSeq}")
    @ApiOperation(value = "자산 삭제", notes = "assetSeq을 통한 자산 삭제")
    public ApiResponse removeAsset(@PathVariable("assetSeq") long assetSeq) throws Exception {
        Boolean result = assetService.removeAsset(assetSeq);
        
        ApiResponse response = new ApiResponse(result, HttpStatus.OK, "SUCCES");
        return response;
    }

}
