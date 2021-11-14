package com.accountbook.domain.entity;

import com.accountbook.domain.enums.EventType;
import com.accountbook.dto.user.CategoryRequest;
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

    // 생성자 메서드
    public static UserCategory createUserCategory (User user, Category category) {
        UserCategory userCategory = new UserCategory();

        userCategory.changeUser(user);
        userCategory.category = category;

        return userCategory;
    }

    // 연관 관계 메서드
    public void changeUserCategory(User user, Category category) {
        changeUser(user);
        changeCategory(category);
    }

    private void changeUser(User user) {
        this.user = user;
        user.getUserCategoryList().add(this);
    }

    public void changeCategory(Category category) {
        this.category = category;
    }
}
