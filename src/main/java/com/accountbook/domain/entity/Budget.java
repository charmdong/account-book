package com.accountbook.domain.entity;

import com.accountbook.domain.enums.PeriodType;
import com.accountbook.dto.Budget.BudgetRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "budget")
public class Budget extends BaseInfo{

    @Id
    @GeneratedValue
    @Column(name = "BUDGET_SEQ")
    private Long seq;

    @OneToOne
    @JoinColumn(name = "USER_CATEGORY_SEQ")
    private Category category;

    @Enumerated(EnumType.STRING)
    private PeriodType periodType;

    private Long amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    //생성 메소드
    public static Budget createBudget(BudgetRequest budgetRequest, Category category, User user) {
        Budget budget = new Budget();
        budget.category = category;
        budget.periodType = budgetRequest.getPeriodType();
        budget.amount =  budgetRequest.getAmount();
        budget.setUser(user);
        return budget;
    }

    //연관 관계 메소드
    private void setUser(User user) {
        this.user = user;
        //user.getBudgetList().add(this);
    }

    //비즈니스 로직
    public void changeBudget(BudgetRequest budgetRequest, Category category){
        this.category = category;
        this.periodType = budgetRequest.getPeriodType();
        this.amount =  budgetRequest.getAmount();
    }

    public void removeBudget(User user){
        //user.getBudgetList().remove(this);
    }
}
