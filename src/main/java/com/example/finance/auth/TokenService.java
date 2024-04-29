package com.example.finance.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.finance.configuration.ClockProvider;
import com.example.finance.exception.model.AuthorizationException;
import com.example.finance.model.entity.UserAccountEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class TokenService {

    private static final String BEARER_PREFIX = "Bearer ";
    private static final String ALGORYTHM_KEY = "ALG";
    private static final String ALGORYTHM_VALUE = "HMAC512";
    private static final Algorithm ALGORYTHM = Algorithm.HMAC512("1016@#$asdzxcjo");
    private static final long EXPIRED_TIME_MILLIS = 30 * 60 * 1000;

    private final ClockProvider clock;

    public String generateBearerToken(UserAccountEntity user) {
        Map<String, Object> claims = ClaimsStrategy.getClaims(user);
        return generateToken(claims);
    }

    public String refreshBearerToken(String bearerToken) {
        String token = bearerToken.startsWith(BEARER_PREFIX) ?
                bearerToken.substring(BEARER_PREFIX.length()) : bearerToken;
        DecodedJWT decodedJWT = JWT.decode(token);
        Map<String, Claim> decodedClaims = decodedJWT.getClaims();
        Map<String, Object> claims = decodedClaims.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().as(Object.class)));

        return generateToken(claims);
    }

    public DecodedJWT verify(String bearerToken) {
        if (bearerToken == null) {
            throw new AuthorizationException("Token is null");
        }
        if (!bearerToken.startsWith(BEARER_PREFIX)) {
            throw new AuthorizationException("Token format is incorrect. Expected to start with 'Bearer '");
        }
        String token = bearerToken.substring(BEARER_PREFIX.length());
        try {
            return JWT.require(ALGORYTHM).build().verify(token);
        } catch (JWTVerificationException e) {
            throw new AuthorizationException("Token verification failed");
        }
    }

    private String generateToken(Map<String, Object> claims) {
        return BEARER_PREFIX +
                JWT.create()
                        .withHeader(Map.of(ALGORYTHM_KEY, ALGORYTHM_VALUE))
                        .withPayload(claims)
                        .withExpiresAt(clock.getDate().plusMillis(EXPIRED_TIME_MILLIS))
                        .sign(ALGORYTHM);
    }
}
