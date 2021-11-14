package com.accountbook.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.accountbook.domain.entity.Asset;
import com.accountbook.domain.entity.User;
import com.accountbook.domain.enums.AssetType;

public class AssetDto {
    protected Long seq;

    protected String name;
    protected String memo;
    protected Long balance;

    protected AssetType assetType;

    protected User user;

    protected Boolean initYn;
    protected LocalDateTime initDate;

    private LocalDateTime settlementDate;
    private LocalDateTime paymentDate;
    private Boolean autoYn;

    public AssetDto(Asset asset) {
        this.seq = asset.getSeq();
        this.name = asset.getName();
        this.memo = asset.getMemo();
        this.balance = asset.getBalance();
        this.assetType = asset.getAssetType();
        this.user = asset.getUser();
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
