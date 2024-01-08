package com.akkin.gulbi.domain;

import com.akkin.common.BaseTimeEntity;
import com.akkin.gulbi.dto.request.GulbiUpdateForm;
import com.akkin.member.domain.Member;
import java.time.LocalDateTime;
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

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GulbiCategory category;

    @Column(nullable = false)
    private String saveContent;

    @Column(nullable = false)
    private String how;

    // 아낀 시기가 언제인지 저장
    @Column(nullable = false)
    private LocalDateTime savedAt;

    @Column(nullable = false)
    private Integer expectCost;

    @Column(nullable = false)
    private Integer realCost;

    @Column(nullable = false)
    private Integer saveMoney;

    @Builder
    public Gulbi(final Member member,
                 final String imageUrl,
                 final GulbiCategory category,
                 final String saveContent,
                 final String how,
                 final int year,
                 final int month,
                 final int day,
                 final Integer expectCost,
                 final Integer realCost) {
        this.member = member;
        this.imageUrl = imageUrl;
        this.category = category;
        this.saveContent = saveContent;
        this.how = how;
        this.savedAt = LocalDateTime.of(year, month, day, 0, 0, 0);
        this.expectCost = expectCost;
        this.realCost = realCost;
        this.saveMoney = expectCost - realCost;
    }

    public void updateGulbi(final GulbiUpdateForm form) {
        this.imageUrl = form.getImageUrl();
        this.category = form.getCategory();
        this.saveContent = form.getSaveContent();
        this.how = form.getHow();
        this.savedAt = LocalDateTime.of(form.getYear(), form.getMonth(), form.getDay(), 0, 0, 0);
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
