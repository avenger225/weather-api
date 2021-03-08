package com.project.weather.configuration.authorization;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AuthorizationConfig {

    protected static final String TOKEN_HEADER = "Authorization";
    protected static final String TOKEN_PREFIX = "Bearer ";
    private final long expirationTime;
    private final String secret;

    public AuthorizationConfig(@Value("${jwt.expirationTime}") long expirationTime, @Value("${jwt.secret}") String secret) {
        this.expirationTime = expirationTime;
        this.secret = secret;
    }
}
