package com.accountbook.domain.entity;

import com.accountbook.domain.enums.PeriodType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Budget {

    @Id
    @GeneratedValue
    @Column(name = "BUDGET_SEQ")
    private Long seq;

    @OneToOne
    @JoinColumn(name = "USER_CATEGORY_SEQ")
    private UserCategory category;

    @Enumerated(EnumType.STRING)
    private PeriodType periodType;

    private Long amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
