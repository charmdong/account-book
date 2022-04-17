package com.accountbook.domain.entity;

import com.accountbook.dto.category.CategoryRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.StringUtils;

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
        //user.getCategoryList().add(this);
    }

    public void removeUserCategoryList(User user) {

        //user.getCategoryList().remove(this);
    }

    // 비즈니스 로직 메서드
    public Boolean checkUpdateResult(CategoryRequest request) {

        if (StringUtils.hasText(request.getName())) {
            if (!request.getName().equals(comCategory.getName())) return false;
        }

        if (request.getEventType() != null) {
            if (request.getEventType() != comCategory.getEventType()) return false;
        }

        if (request.getUseYn() != null) {
            if (request.getUseYn() != comCategory.getUseYn()) return false;
        }

        return true;
    }

}
