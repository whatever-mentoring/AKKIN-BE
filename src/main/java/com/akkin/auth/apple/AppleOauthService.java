package com.akkin.auth.apple;

import com.akkin.auth.apple.dto.ApplePublicKeys;
import com.akkin.auth.apple.dto.AppleUser;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.PublicKey;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class AppleOauthService {

    private final AppleTokenParser appleTokenParser;
    private final AppleClient appleClient;
    private final ApplePublicKeyGenerator applePublicKeyGenerator;

    private final String DEFAULT_NAME = "apple";

    public AppleUser createAppleUser(String appleToken) {
        Map<String, String> appleTokenHeader = appleTokenParser.parseHeader(appleToken);
        ApplePublicKeys applePublicKeys = appleClient.getApplePublicKeys();
        PublicKey publicKey = applePublicKeyGenerator.generate(appleTokenHeader, applePublicKeys);
        Claims claims = appleTokenParser.extractClaims(appleToken, publicKey);
        return new AppleUser(DEFAULT_NAME, claims.get("email", String.class));
    }
}
