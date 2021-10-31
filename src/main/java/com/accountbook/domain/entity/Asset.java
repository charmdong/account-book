package com.accountbook.domain.entity;

import com.accountbook.domain.dto.CreateAssetRequest;
import com.accountbook.domain.enums.AssetType;
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
}
