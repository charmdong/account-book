package com.accountbook.domain.entity;

import com.accountbook.domain.enums.AssetType;
import com.accountbook.dto.AssetRequest;
import com.nimbusds.oauth2.sdk.Request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
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
