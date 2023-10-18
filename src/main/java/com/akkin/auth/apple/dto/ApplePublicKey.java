package com.akkin.auth.apple.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApplePublicKey {

    private String kty;

    private String kid;

    private String use;

    private String alg;

    private String n;

    private String e;

    public boolean isSameAlg(String alg) {
        return this.alg.equals(alg);
    }

    public boolean isSameKid(String kid) {
        return this.kid.equals(kid);
    }

    @Builder
    public ApplePublicKey(String kty, String kid, String use, String alg, String n, String e) {
        this.kty = kty;
        this.kid = kid;
        this.use = use;
        this.alg = alg;
        this.n = n;
        this.e = e;
    }
}
