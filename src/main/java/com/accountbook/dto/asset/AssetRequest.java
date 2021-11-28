package com.accountbook.dto.asset;

import java.time.LocalDateTime;

import com.accountbook.domain.entity.User;
import com.accountbook.domain.enums.AssetType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
}
