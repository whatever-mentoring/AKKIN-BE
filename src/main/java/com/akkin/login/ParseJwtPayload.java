package com.akkin.login;

import com.akkin.common.exception.InvalidJwtException;
import com.akkin.common.exception.OauthJwtParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ParseJwtPayload {

    public OauthMemberInfo parse(String jwt) {
        String base64EncodedPayload = getJwtPayload(jwt);
        String decodedPayload = base64Decode(base64EncodedPayload);
        return parsePayload(decodedPayload);
    }

    private String getJwtPayload(String jwt) {
        String[] splitToken = jwt.split("\\.");
        // 올바른 JWT인지 확인
        if (splitToken.length != 3) {
            throw new InvalidJwtException("Invalid JWT token");
        }

        // Payload는 두 번째 부분
        return splitToken[1];
    }

    private String base64Decode(String base64EncodedPayload) {
        return new String(Base64.getUrlDecoder().decode(base64EncodedPayload), StandardCharsets.UTF_8);
    }

    private OauthMemberInfo parsePayload(String decodedPayload) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> payloadMap = objectMapper.readValue(decodedPayload, Map.class);
            return new OauthMemberInfo(payloadMap);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new OauthJwtParseException("Failed to decode payload");
        }
    }
}
