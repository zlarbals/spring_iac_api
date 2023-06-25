package com.example.spring_iac_api.util.jwt;

import io.jsonwebtoken.*;
import com.example.spring_iac_api.util.time.DefaultTimeProvider;
import com.example.spring_iac_api.util.time.TimeProvider;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Slf4j
public class JwtTokenProvider {

    private final String ACCESS_TOKEN_SECRET_KEY = "cdad1199-e4cd-4429-a9cd-c04cacc89156";

    private final String REFRESH_TOKEN_SECRET_KEY = "c9ba7d53-6880-4cfb-a386-3fabd5b1f040";

    private final long ACCESS_TOKEN_EXPIRATION_TIME = 1000L * 60 * 15; // 15 minutes
    private final long REFRESH_TOKEN_EXPIRATION_TIME = 1000L * 60 * 60; // 60 minutes

    private final TimeProvider timeProvider;

    public JwtTokenProvider(TimeProvider timeProvider){
        this.timeProvider = timeProvider;
    }

    public JwtTokenProvider(){
        this.timeProvider = new DefaultTimeProvider();
    }

    public String generateAccessToken(String email){
        return generateToken(email,ACCESS_TOKEN_SECRET_KEY,ACCESS_TOKEN_EXPIRATION_TIME);
    }

    public String generateRefreshToken(String email){
        return generateToken(email,REFRESH_TOKEN_SECRET_KEY,REFRESH_TOKEN_EXPIRATION_TIME);
    }

    private String generateToken(String email, String tokenSecretKey, long expirationTime){
        SecretKey secretKey = Keys.hmacShaKeyFor(tokenSecretKey.getBytes(StandardCharsets.UTF_8));
        Claims claims = Jwts.claims().setSubject(email);
        Date now = timeProvider.getCurrentTime();
        Date expireTime = new Date(now.getTime()+expirationTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expireTime)
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
        boolean result = false;
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            result = !claims.getBody().getExpiration().before(new Date());
        }catch (ExpiredJwtException e){
            log.info("TOKEN EXPIRED");
        }catch (Exception e){
            log.info("TOKEN ERROR");
        }

        return result;
    }

    public String getMemberEmail(String email, String refreshToken) {
        SecretKey refreshTokenSecretKey = Keys.hmacShaKeyFor(REFRESH_TOKEN_SECRET_KEY.getBytes(StandardCharsets.UTF_8));

        String refreshTokenEmail = Jwts.parserBuilder().setSigningKey(refreshTokenSecretKey).build().parseClaimsJws(refreshToken).getBody().getSubject();



        if(refreshTokenEmail.equals(email)){
            return email;
        }else{
            return "";
        }
    }

}
