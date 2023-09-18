package com.akkin.gulbi.create.dto;

import com.akkin.gulbi.Category;
import com.akkin.gulbi.Gulbi;
import com.akkin.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GulbiCreateForm {

    private Integer year;

    private Integer month;

    private Integer day;

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
            .category(category)
            .content(content)
            .how(how)
            .expectCost(expectCost)
            .realCost(realCost)
            .build();
    }

}
