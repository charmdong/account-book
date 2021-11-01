package com.accountbook.dto;

import com.accountbook.domain.entity.GeneralAsset;
import com.accountbook.domain.entity.User;
import com.accountbook.domain.enums.AssetType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class AssetRequest {

    private String name;
    private String memo;
    private Long balance;
    private AssetType assetType;
    private User user;
    private Boolean initYn;
    private LocalDateTime initDate;
    private LocalDateTime settlementDate;
    private LocalDateTime paymentDate;
    private Boolean autoYn;
    private GeneralAsset paymentType;
}
