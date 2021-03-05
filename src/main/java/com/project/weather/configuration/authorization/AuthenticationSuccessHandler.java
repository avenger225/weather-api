package com.project.weather.configuration.authorization;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static com.project.weather.configuration.authorization.AuthorizationConfig.TOKEN_HEADER;
import static com.project.weather.configuration.authorization.AuthorizationConfig.TOKEN_PREFIX;

@Component
@AllArgsConstructor
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final AuthorizationConfig authorizationConfig;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        String token = JWT.create()
                .withSubject(principal.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + authorizationConfig.getExpirationTime()))
                .sign(Algorithm.HMAC256(authorizationConfig.getSecret()));
        response.setContentType("application/json");
        response.getOutputStream().println("{ \"" + TOKEN_HEADER + "\": \"" + TOKEN_PREFIX + token + "\" }");
    }
}
