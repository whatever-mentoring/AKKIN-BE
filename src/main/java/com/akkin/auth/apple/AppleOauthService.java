package com.akkin.auth.apple;

import com.akkin.auth.apple.dto.ApplePublicKeys;
import com.akkin.auth.apple.dto.AppleUser;
import io.jsonwebtoken.Claims;
import java.security.PublicKey;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

@RequiredArgsConstructor
@Component
public class AppleOauthService {

    private final AppleTokenParser appleTokenParser;
    private final AppleClient appleClient;
    private final ApplePublicKeyGenerator applePublicKeyGenerator;

    public AppleUser createAppleUser(String appleToken) {
        appleToken = new String(Base64Utils.decodeFromUrlSafeString(appleToken));
        Map<String, String> appleTokenHeader = appleTokenParser.parseHeader(appleToken);
        ApplePublicKeys applePublicKeys = appleClient.getApplePublicKeys();
        PublicKey publicKey = applePublicKeyGenerator.generate(appleTokenHeader, applePublicKeys);
        Claims claims = appleTokenParser.extractClaims(appleToken, publicKey);
        return new AppleUser(claims.getSubject(), claims.get("email", String.class));
    }
}
