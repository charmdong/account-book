package com.accountbook.api;

import com.accountbook.common.utils.CookieUtils;
import com.accountbook.dto.asset.AssetDto;
import com.accountbook.dto.asset.AssetRequest;
import com.accountbook.dto.response.ApiResponse;
import com.accountbook.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/asset")
public class AssetApiController {
    private final AssetService assetService;

    // 자산 전체 조회
    @GetMapping("")
    public ApiResponse getAssetList(HttpServletRequest request) throws Exception {
        String userId = CookieUtils.getCookieValueByName(request.getCookies(), "userId");
        List<AssetDto> data = assetService.getAssetList(userId);
        ApiResponse response = new ApiResponse(data, HttpStatus.OK, "SUCCES");
        return response;
    }

    // 자산 상세 조회
    @GetMapping("/{assetSeq}")
    public ApiResponse getAssetDetail(@PathVariable("assetSeq") long assetSeq) throws Exception {
        AssetDto data =  assetService.getAsset(assetSeq);

        ApiResponse response = new ApiResponse(data, HttpStatus.OK, "SUCCES");
        return response;
    }

    // 자산 등록
    @PostMapping("")
    public ApiResponse postMethodName(@RequestBody AssetRequest assetRequest) throws Exception {
        Boolean result = assetService.registAsset(assetRequest);
        System.out.println(LocalDateTime.now());

        ApiResponse response = new ApiResponse(result, HttpStatus.OK, "SUCCES");
        return response;
    }

    // 자산 수정
    @PutMapping("/{assetSeq}")
    public ApiResponse updateAsset(@PathVariable("assetSeq") long assetSeq, @RequestBody @Valid AssetRequest assetRequest) throws Exception {
        AssetDto data = assetService.updateAseet(assetSeq, assetRequest);

        ApiResponse response = new ApiResponse(data, HttpStatus.OK, "SUCCES");
        return response;
    }

    // 자산 삭제
    @DeleteMapping("/{assetSeq}")
    public ApiResponse removeAsset(@PathVariable("assetSeq") long assetSeq) throws Exception {
        Boolean result = assetService.removeAsset(assetSeq);
        
        ApiResponse response = new ApiResponse(result, HttpStatus.OK, "SUCCES");
        return response;
    }

}
