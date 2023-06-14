package com.example.spring_iac_api.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
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

    public boolean validateAccessToken(String accessToken){
        SecretKey secretKey = Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        return validateToken(accessToken,secretKey);
    }

    public boolean validateRefreshToken(String refreshToken){
        SecretKey secretKey = Keys.hmacShaKeyFor(REFRESH_TOKEN_SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        return validateToken(refreshToken,secretKey);
    }
    private boolean validateToken(String token, Key key){
        Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        return !claims.getBody().getExpiration().before(new Date());
    }

    public String getMemberEmail(String accessToken, String refreshToken) {
        SecretKey accessTokenSecretKey = Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        SecretKey refreshTokenSecretKey = Keys.hmacShaKeyFor(REFRESH_TOKEN_SECRET_KEY.getBytes(StandardCharsets.UTF_8));

        String accessTokenEmail = Jwts.parserBuilder().setSigningKey(accessTokenSecretKey).build().parseClaimsJws(accessToken).getBody().getSubject();
        String refreshTokenEmail = Jwts.parserBuilder().setSigningKey(refreshTokenSecretKey).build().parseClaimsJws(refreshToken).getBody().getSubject();

        if(accessTokenEmail.equals(refreshTokenEmail)){
            return accessTokenEmail;
        }else{
            return "";
        }
    }

}
