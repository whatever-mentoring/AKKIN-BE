package com.akkin.auth;

import com.akkin.auth.dto.AuthToken;
import com.akkin.common.exception.UnauthorizedException;
import com.akkin.login.dto.AuthMember;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    private RedisService redisService;

    @Around("@annotation(AuthRequired)")
    public Object handleAuth(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest request = getCurrentRequest();

        AuthMember authMember = getAuthMemberFromAccessToken(request.getHeader("accessToken"));

        if (authMember == null){
            authMember = handleRefreshToken(request.getHeader("refreshToken"));
        }

        if (authMember == null) {
            throw new UnauthorizedException("유효하지 않은 refresh 토큰");
        }
        
        request.setAttribute("authMember", authMember);
        return pjp.proceed();
    }

    private HttpServletRequest getCurrentRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    private AuthMember getAuthMemberFromAccessToken(String accessToken) {
        if (accessToken == null) {
            throw new UnauthorizedException("Access token 없음");
        }
        return redisService.getAuthMember(accessToken);
    }

    private AuthMember handleRefreshToken(String refreshToken) {
        if (refreshToken == null) {
            throw new UnauthorizedException("Refresh token 없음");
        }

        AuthToken authToken = redisService.checkRefreshToken(refreshToken);
        if (authToken == null) {
            throw new UnauthorizedException("Auth token 재발급 오류");
        }

        HttpServletResponse response = getCurrentResponse();
        response.addHeader("accessToken", authToken.getAccessToken());
        response.addHeader("refreshToken", authToken.getRefreshToken());

        return redisService.getAuthMember(authToken.getAccessToken());
    }

    private HttpServletResponse getCurrentResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }
}
