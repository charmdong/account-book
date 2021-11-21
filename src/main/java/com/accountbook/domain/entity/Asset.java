package com.accountbook.domain.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.accountbook.domain.enums.AssetType;
import com.accountbook.dto.AssetRequest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@Inheritance
@DiscriminatorColumn
@NoArgsConstructor
public class Asset {

    @Id
    @GeneratedValue
    @Column(name = "ASSET_SEQ")
    private Long seq;

    private String name;
    private String memo;
    private Long balance;

    @Enumerated(EnumType.STRING)
    private AssetType assetType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    private Boolean initYn;
    private LocalDateTime initDate;

    private LocalDateTime settlementDate;
    private LocalDateTime paymentDate;
    private Boolean autoYn;

    public void changeAsset(AssetRequest request) {
        this.name = request.getName();
        this.memo = request.getMemo();
        this.balance = request.getBalance();
        this.assetType = request.getAssetType();
        this.initYn = request.getInitYn();
        this.initDate = request.getInitDate();
        this.settlementDate = request.getSettlementDate();
        this.paymentDate = request.getPaymentDate();
        this.autoYn = request.getAutoYn();
    }

    public static Asset createAsset(AssetRequest assetRequest) {
        Asset asset = new Asset();

        asset.name = assetRequest.getName();
        asset.memo = assetRequest.getMemo();
        asset.balance = assetRequest.getBalance();
        asset.assetType = assetRequest.getAssetType();
        asset.user = assetRequest.getUser();
        asset.initYn = assetRequest.getInitYn();
        asset.initDate = assetRequest.getInitDate();
        asset.settlementDate = assetRequest.getSettlementDate();
        asset.paymentDate = assetRequest.getPaymentDate();
        asset.autoYn = assetRequest.getAutoYn();

        return asset;
    }

}
