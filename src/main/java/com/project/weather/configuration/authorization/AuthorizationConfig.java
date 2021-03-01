package com.project.weather.configuration.authorization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizationConfig {
    protected static final String TOKEN_HEADER = "Authorization";
    protected static final String TOKEN_PREFIX = "Bearer ";

    @Value("${jwt.expirationTime}")
    private long expirationTime;
    @Value("${jwt.secret}")
    private String secret;
}
