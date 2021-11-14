package com.accountbook.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Category {

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
    public static Category createUserCategory (User user, ComCategory comCategory) {
        Category category = new Category();

        category.changeUser(user);
        category.comCategory = comCategory;

        return category;
    }

    // 연관 관계 메서드
    public void changeUserCategory(User user, ComCategory comCategory) {
        changeUser(user);
        changeCategory(comCategory);
    }

    private void changeUser(User user) {
        this.user = user;
        user.getCategoryList().add(this);
    }

    public void changeCategory(ComCategory comCategory) {
        this.comCategory = comCategory;
    }
}
