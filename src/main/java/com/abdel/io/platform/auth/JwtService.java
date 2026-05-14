package com.abdel.io.platform.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

    private final String secret;

    public JwtService( @Value("${jwt.secret}") String secret) {
        this.secret = secret;
    }

    public String generateToken(UUID userId) {
        Key key = Keys.hmacShaKeyFor(secret.getBytes());

        return Jwts.builder()
                .subject(userId.toString())
                .issuedAt(new Date())
                .expiration(Date.from(
                        Instant.now().plusMillis(1000 * 60 * 60 * 24)
                ))
                .signWith(key)
                .compact();
    }

    public UUID extractUserId(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        String id = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();

        return UUID.fromString(id);
    }
}