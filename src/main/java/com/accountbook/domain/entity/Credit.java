package com.accountbook.domain.entity;

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
public class Credit extends Asset {

    private LocalDateTime settlementDate;
    private LocalDateTime paymentDate;
    private Boolean autoYn;

    @OneToOne
    @JoinColumn(name = "PAY_ASSET_TYPE")
    private NonCredit paymentType;
}
