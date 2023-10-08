package com.akkin.auth.login.apple.dto;

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
}
