package com.akkin.gulbi.domain;

import com.akkin.common.BaseTimeEntity;
import com.akkin.gulbi.dto.request.GulbiUpdateForm;
import com.akkin.member.domain.Member;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class Gulbi extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(nullable = false)
    private Integer saveYear;

    @Column(nullable = false)
    private Integer saveMonth;

    @Column(nullable = false)
    private Integer saveDay;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String how;

    @Column(nullable = false)
    private Integer expectCost;

    @Column(nullable = false)
    private Integer realCost;

    @Column(nullable = false)
    private Integer saveMoney;

    @Builder
    public Gulbi(   final Member member,
                    final Integer year,
                    final Integer month,
                    final Integer day,
                    final Category category,
                    final String content,
                    final String how,
                    final Integer expectCost,
                    final Integer realCost) {
        this.member = member;
        this.saveYear = year;
        this.saveMonth = month;
        this.saveDay = day;
        this.category = category;
        this.content = content;
        this.how = how;
        this.expectCost = expectCost;
        this.realCost = realCost;
        this.saveMoney = expectCost - realCost;
    }

    public void updateGulbi(final GulbiUpdateForm form) {
        this.saveYear = form.getYear();
        this.saveMonth = form.getMonth();
        this.saveDay = form.getDay();
        this.category = form.getCategory();
        this.content = form.getContent();
        this.how = form.getHow();
        this.expectCost = form.getExpectCost();
        this.realCost = form.getRealCost();
        this.saveMoney = this.expectCost - this.realCost;
    }

    public boolean isWriter(final long access_member_id) {
        if (this.member.getId() != access_member_id) {
            return false;
        }
        return true;
    }

    protected Gulbi() {
    }
}
