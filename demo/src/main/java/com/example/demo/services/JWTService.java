package com.example.demo.services;

import com.example.demo.dto.token.Expiration;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
/**
 * demo
 * Author: Vasylenko Oleksii
 * Date: 26.05.2024
 */
@Service
public class JWTService {

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> function) {
        var claims = extractAllClaims(token);
        return function.apply(claims);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        var usernameFormToken = extractUsername(token);
        return usernameFormToken.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date(System.currentTimeMillis()));
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String generateToken(UserDetails userDetails) {
        long expirationMillis = Expiration.INSTANCE.getExpirationMillis();
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("role", extractRoles(userDetails))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(getSignInKey())
                .compact();
    }

    private SecretKey getSignInKey() {
        return Keys.hmacShaKeyFor("oV9mAhBOO6WRWORtM/OGuKkOYLztGeTe2SsfgpXoNDe2xZZjRg9qpQ+aIu58qVa3HamVFB68NOltnAq7+s/S4A==\n".getBytes());
    }

    public String extractRole(String token) {
        Claims claims = extractAllClaims(token);
        return (String) claims.get("role");
    }

    private String extractRoles(UserDetails userDetails) {
        List<String> roles = userDetails.getAuthorities().stream()
                .map(Object::toString)
                .toList();
        return roles.get(0);
    }

}
