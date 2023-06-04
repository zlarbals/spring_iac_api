package com.example.spring_iac_api.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret-key.access-token}")
    private String ACCESS_TOKEN_SECRET_KEY;

    @Value("${jwt.secret-key.refresh-token}")
    private String REFRESH_TOKEN_SECRET_KEY;

    private final long ACCESS_TOKEN_EXPIRATION_TIME = 15 * 60; // 15 minutes
    private final long REFRESH_TOKEN_EXPIRATION_TIME = 60 * 60; // 60 minutes

    public String generateAccessToken(String email){
        return generateToken(email,ACCESS_TOKEN_SECRET_KEY,ACCESS_TOKEN_EXPIRATION_TIME);
    }

    public String generateRefreshToken(String email){
        return generateToken(email,REFRESH_TOKEN_SECRET_KEY,REFRESH_TOKEN_EXPIRATION_TIME);
    }

    private String generateToken(String email, String tokenSecretKey, long expirationTime){
        SecretKey secretKey = Keys.hmacShaKeyFor(tokenSecretKey.getBytes(StandardCharsets.UTF_8));
        Claims claims = Jwts.claims().setSubject(email);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expireTime = now.plusSeconds(expirationTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(expireTime.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(secretKey,SignatureAlgorithm.HS256)
                .compact();
    }

}
