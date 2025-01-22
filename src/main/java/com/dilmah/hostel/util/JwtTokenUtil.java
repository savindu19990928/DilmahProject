package com.dilmah.hostel.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil {

    private final String secretKey = "nibmgrouponedilmahproject";
    private final long expirationTime = 1000L * 60 * 60 * 24 * 31; // 1 month
    private SecretKey key;
    
    // Constructor to initialize the key
    public JwtTokenUtil() {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // Generate JWT Token
    public String generateToken(String username, String role, String ipAddress) {
        return Jwts.builder()
        		.subject(username)
                .claim("role", role)
                .claim("ip", ipAddress) // Store client IP in JWT
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    // Extract Claims from Token
    public Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims("jwt")
                .getPayload();
    }

    // Extract username from Token
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }
    
    // Extract role from Token
    public String extractRole(String token) {
        return extractClaims(token).get("role", String.class);
    }

    // Validate if the token is expired
    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    // Validate Token
    public boolean validateToken(String token, String username, String ipAddress) {
        String tokenUsername = extractUsername(token);
        String tokenIpAddress = extractClaims(token).get("ip", String.class);
        return (username.equals(tokenUsername) && !isTokenExpired(token) && ipAddress.equals(tokenIpAddress));
    }
}
