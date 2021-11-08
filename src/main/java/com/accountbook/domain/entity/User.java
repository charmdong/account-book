package com.accountbook.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "USER_ID")
    private String id;

    private String password;
    private String name;
    private String email;
    private LocalDateTime birthDate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private final List<Budget> budgetList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private final List<Asset> assetList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private final List<UserCategory> userCategoryList = new ArrayList<>();
}
