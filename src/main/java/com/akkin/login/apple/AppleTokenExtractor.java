package com.akkin.login.apple;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AppleTokenExtractor {

    private static final String JWT_SPLIT = "\\.";
    private static final int HEADER_INDEX = 0;

    private final ObjectMapper objectMapper;

    public Map<String, String> extractHeader(String appleToken) {
        try {
            String encodedHeader = appleToken.split(JWT_SPLIT)[HEADER_INDEX];
            String decodedHeader = new String(Base64.getUrlDecoder().decode(encodedHeader));
            return objectMapper.readValue(decodedHeader, Map.class);
        } catch (JsonMappingException e) {
            throw new RuntimeException("Invalid Header");
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Invalid base64 decode");
        }
    }

    public Claims extractClaims(String appleToken, PublicKey publicKey) {
        try {
            JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build();
            return jwtParser.parseClaimsJws(appleToken).getBody();
        } catch (Exception e) {
            // todo: 애플 로그인 성공하면 exception 나누기
            throw new RuntimeException("애플 사용자 claim 추출 오류");
        }
    }

}
