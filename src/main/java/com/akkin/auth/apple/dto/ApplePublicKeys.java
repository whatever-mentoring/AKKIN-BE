package com.akkin.auth.apple.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class ApplePublicKeys {

    // 불변성 보장
    private final List<ApplePublicKey> keys;

    public ApplePublicKeys(List<ApplePublicKey> keys) {
        this.keys = List.copyOf(keys);
    }

    public ApplePublicKey getMatchingKey(String alg, String kid) {
        return keys.stream()
            .filter(key -> key.isSameAlg(alg) && key.isSameKid(kid))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("잘못된 토큰 형태입니다."));
    }
}
