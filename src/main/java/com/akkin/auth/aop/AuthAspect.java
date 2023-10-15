package com.akkin.auth.aop;

import static com.akkin.auth.token.AuthTokenService.accessTokenMap;

import com.akkin.auth.dto.AuthMember;
import com.akkin.auth.token.AuthToken;
import com.akkin.auth.token.AuthTokenService;
import com.akkin.common.exception.UnauthorizedException;
import com.akkin.member.Member;
import com.akkin.member.MemberService;
import java.time.Duration;
import java.time.LocalDateTime;
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

    @Autowired
    private MemberService memberService;

    private final String ACCESS_TOKEN_HEADER = "accessToken";
    private final String REFRESH_TOKEN_HEADER = "refreshToken";
    private final String AUTH_MEMBER_ATTRIBUTE_NAME = "authMember";

    @Around("@annotation(AuthRequired)")
    public Object handleAuth(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest request = getCurrentRequest();
        String accessToken = parseAccessToken(request);
        String refreshToken = parseRefreshToken(request);

        AuthMember authMember = getAuthMember(accessToken, refreshToken);
        request.setAttribute(AUTH_MEMBER_ATTRIBUTE_NAME, authMember);
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
            return checkRefreshToken(accessToken, refreshToken);
        }
        // access 토큰 검증
        if (isAccessTokenValid(authMember.getCreatedAt())) {
            return authMember;
        }
        // access 토큰이 만료됐다면 refresh 토큰 검증
        AuthToken authToken = authTokenService.getAuthToken(accessToken, refreshToken);
        if (isRefreshTokenValid(authToken.getExpiredAt())) {
            // 기존 인증 캐시 삭제
            accessTokenMap.remove(accessToken);
            // 새로운 인증 토큰 재발급 및 DB 갱신
            authTokenService.updateAuthToken(authToken);
            accessTokenMap.put(authToken.getAccessToken(), authMember);
            return authMember;
        }
        throw new UnauthorizedException("로그인 필요");
    }

    private AuthMember checkRefreshToken(String accessToken, String refreshToken) {
        // DB에 저장된 인증 정보가 있는지 확인
        AuthToken authToken = authTokenService.getAuthToken(accessToken, refreshToken);
        // 리프레시 토큰이 유효하면 인증 토큰 재발급
        if (isRefreshTokenValid(authToken.getExpiredAt())) {
            Member member = memberService.findMember(authToken.getMemberId());
            // 기존 인증 캐시 삭제
            accessTokenMap.remove(accessToken);
            // 새로운 인증 토큰 재발급 및 DB 갱신
            authTokenService.updateAuthToken(authToken);
            AuthMember authMember = new AuthMember(member);
            accessTokenMap.put(authToken.getAccessToken(), authMember);
            return authMember;
        }
        throw new UnauthorizedException("로그인 필요");
    }

    private boolean isAccessTokenValid(LocalDateTime accessTokenCreatedAt) {
        LocalDateTime now = LocalDateTime.now();
        if (Duration.between(accessTokenCreatedAt, now).toSeconds() > 3600) {
            return false;
        }
        return true;
    }

    private boolean isRefreshTokenValid(LocalDateTime refreshTokenExpiredAt) {
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(refreshTokenExpiredAt)) {
            return false;
        }
        return true;
    }
}
