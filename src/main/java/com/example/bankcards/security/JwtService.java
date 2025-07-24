package com.example.bankcards.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String SECRET_KEY = "YourSuperSecretKeyForJwtGeneration1234567890!"; // минимум 32 символа

    // Ключ для подписи токена
    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // Генерация токена
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)) // 24 часа
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Вытаскиваем username из токена
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Вытаскиваем роль из токена
    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    // Проверка валидности токена (и не просрочен ли)
    public boolean isTokenValid(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username)) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Универсальный метод извлечения claim-а
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
