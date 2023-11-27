package com.akkin.auth.apple;

import com.akkin.auth.apple.dto.AppleTokenResponse;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@Component
public class AppleRevokeService {

    private final ApplePrivateKeyGenerator privateKeyGenerator;

    @Value("${spring.oauth.apple.bundle}")
    private String bundle;

    public void revoke(AppleTokenResponse appleToken) {
        if (appleToken == null) {
            log.info("탈퇴 요청에 전달할 appleToken 이 존재하지 않습니다.");
            return;
        }
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        String revokeUrl = "https://appleid.apple.com/auth/revoke";

        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", bundle);
        params.add("client_secret", privateKeyGenerator.generate());
        params.add("token", appleToken.getAccessToken());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

        restTemplate.postForEntity(revokeUrl, httpEntity, String.class);
    }
}
