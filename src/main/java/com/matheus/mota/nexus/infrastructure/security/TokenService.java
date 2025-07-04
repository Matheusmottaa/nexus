package com.matheus.mota.nexus.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.matheus.mota.nexus.common.exception.InvalidTokenException;
import com.matheus.mota.nexus.domain.model.RoleEntity;
import com.matheus.mota.nexus.domain.model.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-token-expiration}")
    private Long accessTokenExpiration;

    @Value("${jwt.refresh-token-expiration}")
    private Long refreshTokenExpiration;

    private Algorithm algorithm() {
        return Algorithm.HMAC256(secret.getBytes());
    }

    public String generateAccessToken(UserEntity user) {
        Instant now = Instant.now();
        Instant expiry = now.plus(accessTokenExpiration, ChronoUnit.SECONDS);
        try {
            return JWT
                    .create()
                    .withIssuer("nexus-api")
                    .withSubject(user.getUsername())
                    .withClaim("userId", user.getId().toString())
                    .withClaim("roles", user.getRoles().stream().map(RoleEntity::getName).toList())
                    .withIssuedAt(now)
                    .withExpiresAt(expiry)
                    .sign(algorithm());
        }catch(JWTCreationException e) {
            throw new RuntimeException("Could not create token", e);
        }
    }

    public String generateRefreshToken(UserEntity user) {
        Instant now = Instant.now();
        Instant expiry = now.plus(accessTokenExpiration, ChronoUnit.SECONDS);
        try {
            return JWT
                    .create()
                    .withIssuer("nexus-api")
                    .withSubject(user.getUsername())
                    .withClaim("userId", user.getId().toString())
                    .withClaim("roles", user.getRoles().stream().map(RoleEntity::getName).toList())
                    .withIssuedAt(now)
                    .withExpiresAt(expiry)
                    .sign(algorithm());
        }catch(JWTCreationException e) {
            throw new RuntimeException("Could not create token", e);
        }
    }

    public String validateToken(String token) {
        try {
            return JWT
                    .require(algorithm())
                    .withIssuer("nexus-api")
                    .build()
                    .verify(token)
                    .getSubject();
        }catch(JWTVerificationException e) {
            throw new InvalidTokenException("Invalid or expired token", e);
        }
    }

    public LocalDateTime extractExpiration(String token) {
        Date expiresAt = JWT.require(algorithm()).build().verify(token).getExpiresAt();
        return expiresAt.toInstant().atZone(ZoneOffset.UTC).toLocalDateTime();
    }

}
