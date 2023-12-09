package com.akkin.gulbi.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GulbiCategory {
    DINING("DINING", "식비"),
    TRAFFIC("TRAFFIC", "교통"),
    SHOPPING("SHOPPING", "쇼핑"),
    ETC("ETC", "기타");

    private final String eng;
    private final String kor;

    public static boolean contains(String category) {
        for (GulbiCategory c : GulbiCategory.values()) {
            if (c.getEng().equals(category)) {
                return true;
            }
        }
        return false;
    }
}
