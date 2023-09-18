package com.akkin.login;

import com.akkin.common.exception.AppleOauthLoginException;
import com.akkin.common.exception.OauthFailException;
import com.akkin.login.dto.AuthorizationResponse;
import com.akkin.login.dto.OauthMemberInfo;
import com.akkin.login.dto.TokenResponse;
import com.akkin.member.Member;
import com.akkin.member.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class OauthService {

    private final String APPLE_API_URL = "https://appleid.apple.com/auth/token";
    private final String CLIENT_ID = "";
    private final String CLIENT_SECRET = "";
    private final String REDIRECT_URI = "";

    private final ParseJwtPayload parseJwtPayload;
    private final MemberService memberService;

    public OauthMemberInfo authCode(String code) throws Exception {

        HttpURLConnection connection = createHttpConnection();
        accessAPIServer(connection, code);
        if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            parseErrorResponse(connection);
        }
        return parseSuccessResponse(connection);
    }

    private HttpURLConnection createHttpConnection() {
        try {
            URL url = new URL(APPLE_API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            return conn;
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new OauthFailException("HttpURLConnection 생성 오류");
        }
    }

    private void accessAPIServer(HttpURLConnection connection, String code) {
        String params = String.format("code=%s&client_id=%s&client_secret=%s&redirect_uri=%s&grant_type=authorization_code",
                code, CLIENT_ID, CLIENT_SECRET, REDIRECT_URI);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = params.getBytes("utf-8");
            os.write(input, 0, input.length);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new OauthFailException("response 받아오기 오류");
        }
    }

    private OauthMemberInfo parseSuccessResponse(HttpURLConnection connection) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            ObjectMapper mapper = new ObjectMapper();
            TokenResponse tokenResponse = mapper.readValue(br, TokenResponse.class);
            return parseJwtPayload.parse(tokenResponse.getId_token());
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new AppleOauthLoginException("response 파싱 에러");
        }
    }

    private void parseErrorResponse(HttpURLConnection connection) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
            StringBuilder errorMessage = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                errorMessage.append(line).append('\n');
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            throw new AppleOauthLoginException("구글 로그인 오류");
        }
    }

}
