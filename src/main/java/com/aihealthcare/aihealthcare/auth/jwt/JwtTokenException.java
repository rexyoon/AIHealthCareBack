package com.aihealthcare.aihealthcare.auth.jwt;

import lombok.Getter;

@Getter
public class JwtTokenException extends RuntimeException {

    private final JwtTokenError error;

    public JwtTokenException(JwtTokenError error, Throwable cause) {
        super(error.name(), cause);
        this.error = error;
    }
}
