package com.accountbook.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

/**
 * Category
 *
 * @author donggun
 * @since 2021/11/15
 */
@Entity
@Getter
@ToString(of = {"seq", "comCategory"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseTimeInfo {

    @Id
    @GeneratedValue
    @Column(name = "USER_CATEGORY_SEQ")
    private Long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToOne
    @JoinColumn(name = "CATEGORY_CODE")
    private ComCategory comCategory;

    // 생성자 메서드
    public static Category createCategory (User user, ComCategory comCategory) {
        Category category = new Category();

        category.changeUser(user);
        category.changeComCategory(comCategory);

        return category;
    }

    // 연관 관계 메서드
    public void changeComCategory(ComCategory comCategory) {

        this.comCategory = comCategory;
    }

    public void changeUser(User user) {

        this.user = user;
        user.getCategoryList().add(this);
    }

    public void updateUserCategoryList(User user) {

        user.getCategoryList().remove(this);
    }
}
