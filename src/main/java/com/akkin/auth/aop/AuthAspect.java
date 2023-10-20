package com.akkin.auth.aop;

import static com.akkin.auth.token.AuthTokenService.accessTokenMap;

import com.akkin.auth.dto.AuthMember;
import com.akkin.auth.token.AuthToken;
import com.akkin.auth.token.AuthTokenService;
import com.akkin.common.exception.UnauthorizedException;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class AuthAspect {

    @Autowired
    private AuthTokenService authTokenService;

    public static final String AUTH_MEMBER_ATTRIBUTE = "authMember";
    public static final String ACCESS_TOKEN_HEADER = "accessToken";
    public static final String REFRESH_TOKEN_HEADER = "refreshToken";

    @Around("@annotation(AuthRequired)")
    public Object handleAuth(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest request = getCurrentRequest();
        String accessToken = parseAccessToken(request);
        String refreshToken = parseRefreshToken(request);

        AuthMember authMember = getAuthMember(accessToken, refreshToken);
        request.setAttribute(AUTH_MEMBER_ATTRIBUTE, authMember);
        return pjp.proceed();
    }

    private HttpServletRequest getCurrentRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    private String parseAccessToken(HttpServletRequest request) {
        String accessToken = request.getHeader(ACCESS_TOKEN_HEADER);
        if (accessToken == null) {
            throw new UnauthorizedException("Access token 없음");
        }
        return accessToken;
    }

    private String parseRefreshToken(HttpServletRequest request) {
        String refreshToken = request.getHeader(REFRESH_TOKEN_HEADER);
        if (refreshToken == null) {
            throw new UnauthorizedException("Refresh token 없음");
        }
        return refreshToken;
    }

    private AuthMember getAuthMember(String accessToken, String refreshToken) {
        AuthMember authMember = accessTokenMap.get(accessToken);
        // WAS 재시작 등으로 인해 로컬 캐시가 날아간 이후에 발생하는 인증 처리
        if (authMember == null) {
            AuthToken authToken = authTokenService.getAuthToken(accessToken, refreshToken);
            authTokenService.reIssueAuthToken(authToken);
            return accessTokenMap.get(authToken.getAccessToken());
        }
        // access 토큰 검증
        if (authTokenService.isAccessTokenValid(authMember.getCreatedAt())) {
            return authMember;
        }
        // access 토큰이 만료됐다면 refresh 토큰 검증
        AuthToken authToken = authTokenService.getAuthToken(accessToken, refreshToken);
        if (authTokenService.isRefreshTokenValid(authToken.getExpiredAt())) {
            authToken = authTokenService.reIssueAuthToken(authToken);
            return accessTokenMap.get(authToken.getAccessToken());
        }
        throw new UnauthorizedException("로그인 필요");
    }
}
