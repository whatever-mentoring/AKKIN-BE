package com.akkin.auth.aop;

import static com.akkin.auth.whitelist.WhiteTokenService.accessTokenMap;

import com.akkin.auth.dto.AuthMember;
import com.akkin.auth.dto.response.AuthToken;
import com.akkin.auth.whitelist.WhiteToken;
import com.akkin.auth.whitelist.WhiteTokenService;
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
    private WhiteTokenService whiteTokenService;

    @Autowired
    private MemberService memberService;

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
        // WAS 재시작 등으로 인해 로컬 캐시가 날아간 이후에 발생하는 인증 처리
        if (authMember == null) {
            return checkRefreshToken(accessToken, refreshToken);
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

    private AuthMember checkRefreshToken(String accessToken, String refreshToken) {
        WhiteToken whiteToken = whiteTokenService.getWhiteToken(accessToken, refreshToken);
        Member member = memberService.findMemberOrElseThrow(whiteToken.getMemberId());
        AuthToken authToken = new AuthToken();
        AuthMember authMember = new AuthMember(member);
        accessTokenMap.remove(accessToken);
        accessTokenMap.put(authToken.getAccessToken(), authMember);
        whiteTokenService.updateWhiteToken(whiteToken, authToken);
        return authMember;
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
