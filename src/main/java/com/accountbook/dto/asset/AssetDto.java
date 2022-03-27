package com.accountbook.dto.asset;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.accountbook.domain.entity.Asset;
import com.accountbook.domain.enums.AssetType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class AssetDto {
    private Long seq;

    @Schema(description = "자산 이름")
    private String name;
    private String memo;
    @Schema(description = "잔액")
    private Long balance;

    @Schema(description = "자산 형태")
    private AssetType assetType;

    @Schema(description = "정기적인 자산 초기화 여부")
    private Boolean initYn;
    @Schema(description = "정기적인 자산 초기화 일자")
    private LocalDateTime initDate;

    @Schema(description = "결제일")
    private LocalDateTime settlementDate;
    @Schema(description = "결제일")
    private LocalDateTime paymentDate;
    private Boolean autoYn;

    public AssetDto(Asset asset) {
        this.seq = asset.getSeq();
        this.name = asset.getName();
        this.memo = asset.getMemo();
        this.balance = asset.getBalance();
        this.assetType = asset.getAssetType();
        this.initYn = asset.getInitYn();
        this.initDate = asset.getInitDate();
        this.settlementDate = asset.getSettlementDate();
        this.paymentDate = asset.getPaymentDate();
        this.autoYn = asset.getAutoYn();
    }

    public static List<AssetDto> convertAssetList(List<Asset> asset) {
        List<AssetDto> assetDto = new ArrayList<>();

        for (Asset entity : asset) {
            assetDto.add(new AssetDto(entity));
        }
        return assetDto;
    }
}
