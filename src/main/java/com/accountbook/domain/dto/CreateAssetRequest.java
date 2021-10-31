package com.accountbook.domain.dto;

import com.accountbook.domain.entity.GeneralAsset;
import com.accountbook.domain.entity.User;
import com.accountbook.domain.enums.AssetType;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
public class CreateAssetRequest {

    private Long seq;
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
