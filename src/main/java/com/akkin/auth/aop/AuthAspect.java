package com.akkin.auth.aop;

import static com.akkin.auth.whitelist.WhiteTokenService.accessTokenMap;

import com.akkin.auth.login.dto.AuthMember;
import com.akkin.auth.login.dto.response.AuthToken;
import com.akkin.auth.whitelist.WhiteToken;
import com.akkin.auth.whitelist.WhiteTokenService;
import com.akkin.common.exception.UnauthorizedException;
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
    private WhiteTokenService whiteTokenService;

    @Around("@annotation(AuthRequired)")
    public Object handleAuth(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest request = getCurrentRequest();
        String accessToken = parseAccessToken(request);
        String refreshToken = parseRefreshToken(request);

        AuthMember authMember = getAuthMember(accessToken, refreshToken);
        request.setAttribute("authMember", authMember);
        return pjp.proceed();
    }

    private HttpServletRequest getCurrentRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    private String parseAccessToken(HttpServletRequest request) {
        String accessToken = request.getHeader("accessToken");
        if (accessToken == null) {
            throw new UnauthorizedException("Access token 없음");
        }
        return accessToken;
    }

    private String parseRefreshToken(HttpServletRequest request) {
        String refreshToken = request.getHeader("refreshToken");
        if (refreshToken == null) {
            throw new UnauthorizedException("Refresh token 없음");
        }
        return refreshToken;
    }

    private AuthMember getAuthMember(String accessToken, String refreshToken) {
        AuthMember authMember = accessTokenMap.get(accessToken);
        if (authMember == null) {
            throw new UnauthorizedException("로그인하지 않은 사용자: " + accessToken);
        }
        // access 토큰 검증
        if (isAccessTokenValid(authMember.getCreatedAt())) {
            return authMember;
        }
        // refresh 토큰 검증
        WhiteToken whiteToken = whiteTokenService.getWhiteToken(authMember.getId(), refreshToken);
        if (isRefreshTokenValid(whiteToken.getExpiredAt())) {
            AuthToken authToken = new AuthToken();
            accessTokenMap.remove(accessToken);
            accessTokenMap.put(authToken.getAccessToken(), authMember);
            whiteTokenService.updateWhiteToken(whiteToken, authToken);
            return authMember;
        }
        throw new UnauthorizedException("로그인 필요");
    }

    private boolean isAccessTokenValid(LocalDateTime accessTokenCreatedAt) {
        LocalDateTime now = LocalDateTime.now();
        if (Duration.between(accessTokenCreatedAt, now).toHours() >= 1) {
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
