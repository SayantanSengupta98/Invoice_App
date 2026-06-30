package com.learn.apigw_service.service;

import com.learn.apigw_service.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Slf4j
@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    public String generateToken(User userDetails) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + (expiration * 60 * 1000));

        log.debug("Token generation time: {}", now);
        log.debug("Token expiration time: {}", expiryDate);

        Map<String, Object> claims = Map.of(
                "userName", userDetails.getUserName(),
                "userEmail", userDetails.getUserEmail());

        return Jwts.builder()
                .header().add("typ", "JWT")
                .and()

                .subject(userDetails.getUserId())
                .claims().add(claims)
                .issuedAt(now)
                .expiration(expiryDate)
                .and()
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );
    }

    public String extractUserid(String token) {
        return String.valueOf(extractAllClaims(token).getSubject());
    }


    public String extractUsername(String token) {
        return String.valueOf(extractAllClaims(token).get("userName"));
    }

    public String extractEmail(String token) {
        return String.valueOf(extractAllClaims(token).get("userEmail"));
    }

    public boolean isTokenValid(String token, User userDetails) {

        String userEmail = extractEmail(token);
        String username = extractUsername(token);

        log.debug("Extracted username from JWT: {}", username);
        log.debug("Extracted user email from JWT: {}", userEmail);

        log.debug("User details username: {}", userDetails.getUserName());
        log.debug("User details email: {}", userDetails.getUserEmail());



        return username.equals(userDetails.getUserName())
                && userEmail.equals(userDetails.getUserEmail())
                && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return extractAllClaims(token)
                .getExpiration()
                .before(new Date());
    }

    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}