package com.accountbook.domain.entity;

import com.accountbook.dto.AssetRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@DiscriminatorValue("CREDIT")
public class CreditAsset extends Asset {

    private LocalDateTime settlementDate;
    private LocalDateTime paymentDate;
    private Boolean autoYn;

    @OneToOne
    @JoinColumn(name = "PAY_ASSET_TYPE")
    private GeneralAsset paymentType;

    public static CreditAsset createCreditAsset(AssetRequest request) {
        CreditAsset credit = new CreditAsset();

        credit.name = request.getName();
        credit.memo = request.getMemo();
        credit.balance = request.getBalance();
        credit.assetType = request.getAssetType();
        credit.user = request.getUser();
        credit.initYn = request.getInitYn();
        credit.initDate = request.getInitDate();
        credit.settlementDate = request.getSettlementDate();
        credit.paymentDate = request.getPaymentDate();
        credit.autoYn = request.getAutoYn();
        credit.paymentType = request.getPaymentType();

        return credit;
    }

    private void changeCreditAsset(AssetRequest request) {
        changeAsset(request);

        this.settlementDate = request.getSettlementDate();
        this.paymentDate = request.getPaymentDate();
        this.autoYn = request.getAutoYn();
        this.paymentType = request.getPaymentType();
    }
}
