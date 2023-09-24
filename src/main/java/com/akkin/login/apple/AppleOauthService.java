package com.akkin.login.apple;

import com.akkin.login.apple.dto.ApplePublicKeys;
import com.akkin.login.apple.dto.AppleUser;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.PublicKey;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class AppleOauthService {

    private final AppleTokenExtractor appleTokenExtractor;
    private final AppleClient appleClient;
    private final ApplePublicKeyGenerator applePublicKeyGenerator;

    public AppleUser createAppleUser(String appleToken) {
        Map<String, String> appleTokenHeader = appleTokenExtractor.extractHeader(appleToken);
        ApplePublicKeys applePublicKeys = appleClient.getApplePublicKeys();
        PublicKey publicKey = applePublicKeyGenerator.generate(appleTokenHeader, applePublicKeys);
        Claims claims = appleTokenExtractor.extractClaims(appleToken, publicKey);
        return new AppleUser(claims.getSubject(), claims.get("email", String.class));
    }
}