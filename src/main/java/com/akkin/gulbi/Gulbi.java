package com.akkin.gulbi;

import com.akkin.common.BaseTimeEntity;
import com.akkin.member.Member;
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

//    @Column(nullable = false) // s3 열릴 때 까지 닫아둠
    private String imageUrl;

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
    public Gulbi(Member member, Integer year, Integer month, Integer day, String imageUrl,
        Category category, String content, String how, Integer expectCost, Integer realCost) {
        this.member = member;
        this.saveYear = year;
        this.saveMonth = month;
        this.saveDay = day;
        this.imageUrl = imageUrl;
        this.category = category;
        this.content = content;
        this.how = how;
        this.expectCost = expectCost;
        this.realCost = realCost;
        this.saveMoney = expectCost - realCost;
    }

    protected Gulbi() {
    }
}
