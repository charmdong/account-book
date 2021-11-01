package com.accountbook.domain.entity;

import com.accountbook.dto.AssetRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor
@DiscriminatorValue("GENERAL")
public class GeneralAsset extends Asset {

    public static GeneralAsset createGeneralAsset(AssetRequest request) {
        GeneralAsset asset = new GeneralAsset();

        asset.name = request.getName();
        asset.memo = request.getMemo();
        asset.balance = request.getBalance();
        asset.assetType = request.getAssetType();
        asset.user = request.getUser();
        asset.initYn = request.getInitYn();
        asset.initDate = request.getInitDate();

        return asset;
    }

    private void changeGeneralInfo(AssetRequest request) {
        changeAsset(request);
    }
}
