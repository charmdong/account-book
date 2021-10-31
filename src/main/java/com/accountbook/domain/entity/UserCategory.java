package com.accountbook.domain.entity;

import com.accountbook.domain.enums.EventType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class UserCategory {

    @Id
    @GeneratedValue
    @Column(name = "USER_CATEGORY_SEQ")
    private Long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToOne
    @JoinColumn(name = "CATEGORY_CODE")
    private Category category;
}
