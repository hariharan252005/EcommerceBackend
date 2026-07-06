package com.example.springEcommerce.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.util.Date;

// This class does 2 things:
// 1. Create a token when user logs in
// 2. Read the email back from a token
@Component
public class JwtUtil {

    // Secret key - like a password to sign tokens
    // Must be at least 32 characters long
    private final String SECRET = "mySecretKey12345mySecretKey12345!!";

    // Create token for this email
    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                // token expires after 24 hours
                .expiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()))
                .compact();
    }

    // Read email from token
    public String getEmailFromToken(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(SECRET.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
