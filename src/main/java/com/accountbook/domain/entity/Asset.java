package com.accountbook.domain.entity;

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
}
