package com.akkin.common.exception;

public class ExpireRefreshTokenException extends RuntimeException {

    public static final String NON_EXISTENT_TOKEN_MSG = "존재하지 않는 refresh 토큰";
    public static final String EXPIRED_TOKEN_MSG = "만료된 refresh 토큰";

    public ExpireRefreshTokenException(String message) {
        super(message);
    }
}
