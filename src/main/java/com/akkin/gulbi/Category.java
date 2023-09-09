package com.akkin.gulbi;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {

    DINING("DINING", "식비"),
    TRAFFIC("TRAFFIC", "교통"),
    SHOPPING("SHOPPING", "쇼핑"),
    HOBBY("HOBBY", "취미"),
    ETC("DINING", "기타");

    private final String eng;
    private final String kor;

}
