package com.akkin.login;

import com.akkin.common.exception.AppleOauthLoginException;
import com.akkin.login.dto.AuthorizationResponse;
import com.akkin.member.Member;
import com.akkin.member.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OauthService {

    private final String APPLE_API_URL = "https://appleid.apple.com/auth/token";
    private final String CLIENT_ID = "";
    private final String CLIENT_SECRET = "";
    private final String REDIRECT_URI = "";

    private final ParseJwtPayload parseJwtPayload;
    private final MemberService memberService;

    public Member authCode(String code) throws Exception {
        URL url = new URL(APPLE_API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setDoOutput(true);

        String params = String.format("code=%s&client_id=%s&client_secret=%s&redirect_uri=%s&grant_type=authorization_code",
            code, CLIENT_ID, CLIENT_SECRET, REDIRECT_URI);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = params.getBytes("utf-8");
            os.write(input, 0, input.length);
        }


        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            ObjectMapper mapper = new ObjectMapper();
            AuthorizationResponse tokenResponse = mapper.readValue(br, AuthorizationResponse.class);

            Map<String, String> userInfoMap = parseJwtPayload.parse(tokenResponse.getId_token());
            System.out.println(userInfoMap.get("name"));
            System.out.println(userInfoMap.get("email"));
            return memberService.saveOrUpdateMember(userInfoMap.get("name"), userInfoMap.get("email"));
        }
        else {
            throw new AppleOauthLoginException("애플 로그인 오류");
        }
    }
}
