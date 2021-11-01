package com.accountbook.domain.entity;

import com.accountbook.domain.enums.AssetType;
import com.accountbook.dto.AssetRequest;
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
    protected Long seq;

    protected String name;
    protected String memo;
    protected Long balance;

    @Enumerated(EnumType.STRING)
    protected AssetType assetType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    protected User user;

    protected Boolean initYn;
    protected LocalDateTime initDate;

    protected void changeAsset(AssetRequest request) {
        this.name = request.getName();
        this.memo = request.getMemo();
        this.balance = request.getBalance();
        this.assetType = request.getAssetType();
        this.initYn = request.getInitYn();
        this.initDate = request.getInitDate();
    }
}
