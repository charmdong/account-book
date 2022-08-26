package com.accountbook.domain.entity;

import com.accountbook.dto.user.UserCreateRequest;
import com.accountbook.dto.user.UserUpdateRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * User
 *
 * @author donggun
 * @since 2022/04/12
 */
@Entity
@Getter
@ToString(of = {"id", "password", "name", "email", "birthDate", "token", "expireDate", "prevIncome", "prevExpenditure", "setting"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class User extends BaseTimeInfo {

    @Id
    @Column(name = "USER_ID")
    private String id;
    private String password;
    private String name;
    private String email;
    private LocalDate birthDate;

    // 자동 로그인 대상 여부 판단
    private String token;
    private LocalDateTime expireDate;
    private String loginIp;

    // 지난 달 수입, 지출 금액
    private Long prevIncome;
    private Long prevExpenditure;

    // 사용자 설정
    @PrimaryKeyJoinColumn
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private CustomSetting setting;

    // 생성자 메서드
    public static User createUser(UserCreateRequest request) {

        User user = new User();

        // 기본 정보
        user.id = request.getId();
        user.password = request.getPassword();
        user.name = request.getName();
        user.email = request.getEmail();
        //user.birthDate = request.getBirthDate();

        // 지난 달 수입, 지출 금액
        user.prevIncome = user.prevExpenditure = 0L;

        return user;
    }

    // 비즈니스 로직 메서드
    public void setSetting(CustomSetting setting) {
        this.setting = setting;
    }


    public void changeUser(UserUpdateRequest request) {

        if(StringUtils.hasText(request.getName())) {
            this.name = request.getName();
        }

        if(StringUtils.hasText(request.getEmail())) {
            this.email = request.getEmail();
        }

        if(StringUtils.hasText(request.getBirthDate())) {

        }
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public Boolean checkPwdUpdate (String password) {
        return password.equals(this.password);
    }

    // // 지난 달 수입, 지출 금액 수정
    // public void updatePrevInfo(UpdatePrevInfoRequest request) {

    //     // 수입
    //     if(request.getEventType().equals(EventType.INCOME)) {
    //         // insert
    //         if(request.getRequestType().equals(RequestType.INSERT)) { // TODO enum? util? EcoEvent public static int?
    //             prevIncome += request.getAfterAmount();
    //         }
    //         // update
    //         else if(request.getRequestType().equals(RequestType.UPDATE)) {
    //             prevIncome -= request.getBeforeAmount();
    //             prevIncome += request.getAfterAmount();
    //         }
    //         // delete
    //         else {
    //             prevIncome -= request.getAfterAmount();
    //         }
    //     }
    //     // 지출
    //     else {
    //         // insert
    //         if(request.getRequestType().equals(RequestType.INSERT)) {
    //             prevExpenditure += request.getAfterAmount();
    //         }
    //         // update
    //         else if(request.getRequestType().equals(RequestType.UPDATE)) {
    //             prevExpenditure -= request.getBeforeAmount();
    //             prevExpenditure += request.getAfterAmount();
    //         }
    //         // delete
    //         else {
    //             prevExpenditure -= request.getAfterAmount();
    //         }
    //     }
    // }
    
    public void updatePrevInfo(Long prevIncome, Long prevExpenditure) {
        this.prevIncome = prevIncome;
        this.prevExpenditure = prevExpenditure;
    }

    public void changeSessionInfo(String token, LocalDateTime expireDate, String loginIp) {
        this.token = token;
        this.expireDate = expireDate;
        this.loginIp = loginIp;
    }
}
