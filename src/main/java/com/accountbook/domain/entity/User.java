package com.accountbook.domain.entity;

import com.accountbook.dto.user.UserCreateRequest;
import com.accountbook.dto.user.UserUpdateRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 사용자 엔티티
 */
@Entity
@Getter
@ToString(of = {"id", "password", "name", "email", "birthDate", "uid", "expireDate", "categoryList"})
@NoArgsConstructor
@DynamicUpdate
public class User extends BaseTimeInfo {

    @Id
    @Column(name = "USER_ID")
    private String id;

    private String password;
    private String name;
    private String email;
    private LocalDateTime birthDate;
    private String uid;
    private LocalDateTime expireDate;

    private String loginIp;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private final List<Budget> budgetList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private final List<Asset> assetList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private final List<Category> categoryList = new ArrayList<>();

    // 생성자 메서드
    public static User createUser(UserCreateRequest request) {

        User user = new User();

        user.id = request.getId();
        user.password = request.getPassword();
        user.name = request.getName();
        user.email = request.getEmail();
        user.birthDate = request.getBirthDate();

        return user;
    }

    // 비즈니스 로직 메서드
    public void changeUser(UserUpdateRequest request) {

        if(StringUtils.hasText(request.getName())) {
            this.name = request.getName();
        }

        if(StringUtils.hasText(request.getEmail())) {
            this.email = request.getEmail();
        }
    }

    public void changePassword(String password) {

        this.password = password;
    }

    public Boolean checkPwdUpdate (String password) {

        return password.equals(this.password);
    }

    public void changeSessionInfo(String uid, LocalDateTime expireDate, String loginIp) {
        this.uid = uid;
        this.expireDate = expireDate;
        this.loginIp = loginIp;
    }
}
