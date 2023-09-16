package com.akkin.common;

import com.akkin.common.exception.AppleOauthLoginException;
import com.akkin.common.exception.ExpireRefreshTokenException;
import com.akkin.common.exception.GulbiNotFoundException;
import com.akkin.common.exception.GulbiNotOwnerException;
import com.akkin.common.exception.InvalidJwtException;
import com.akkin.common.exception.MemberNotFoundException;
import com.akkin.common.exception.OauthJwtParseException;
import com.akkin.common.exception.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private ErrorMessage buildErrorMessage(Exception exception, HttpServletRequest request) {
        log.error(exception.getMessage());
        return ErrorMessage.builder()
                .title(exception.getClass().getSimpleName())
                .detail(exception.getMessage())
                .instance(request.getRequestURI())
                .build();
    }

    @ExceptionHandler({
            ExpireRefreshTokenException.class,
            GulbiNotOwnerException.class,
            UnauthorizedException.class
    })
    public ResponseEntity<ErrorMessage> handleUnAuthorize(Exception e, HttpServletRequest request) {
        return new ResponseEntity<>(buildErrorMessage(e, request), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({
            GulbiNotFoundException.class,
            MemberNotFoundException.class
    })
    public ResponseEntity<ErrorMessage> handleNotFound(Exception e, HttpServletRequest request) {
        return new ResponseEntity<>(buildErrorMessage(e, request), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            AppleOauthLoginException.class,
            InvalidJwtException.class,
            OauthJwtParseException.class
    })
    public ResponseEntity<ErrorMessage> handleInternalServerError(Exception e, HttpServletRequest request) {
        return new ResponseEntity<>(buildErrorMessage(e, request), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
