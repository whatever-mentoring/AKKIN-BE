package com.akkin.gulbi.dto;

import com.akkin.gulbi.Category;
import com.akkin.gulbi.Gulbi;
import com.akkin.member.Member;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GulbiUpdateForm {

    private Integer year;

    private Integer month;

    private Integer day;

    private String imageUrl;

    private Category category;

    private String content;

    private String how;

    private Integer expectCost;

    private Integer realCost;

    public Gulbi dtoToEntity(Member member) {
        return Gulbi.builder()
                .member(member)
                .year(year)
                .month(month)
                .day(day)
                .imageUrl(imageUrl)
                .category(category)
                .content(content)
                .how(how)
                .expectCost(expectCost)
                .realCost(realCost)
                .build();
    }
}
