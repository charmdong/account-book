package com.accountbook.domain.entity;

import com.accountbook.dto.user.UserRequest;
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
    private final List<Category> categoryList = new ArrayList<>();

    // 생성자 메서드
    public static User createUser(UserRequest request) {
        User user = new User();

        user.id = request.getId();
        user.password = request.getPassword();
        user.name = request.getName();
        user.email = request.getEmail();
        user.birthDate = request.getBirthDate();

        return user;
    }

    // 비즈니스 로직 메서드
    public void changeUser(UserRequest request) {
        this.name = request.getName();
        this.email = request.getEmail();
    }

    public void changePassword(String password) {
        this.password = password;
    }

    // 연관 관계 메서드 -> budget, asset, userCategory 쪽에서 구현하는 게 맞을 거 같다.
}
