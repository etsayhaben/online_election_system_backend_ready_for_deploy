package com.onlineelection.system.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    // Generate a secure 256-bit secret key using Keys.secretKeyFor
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Secure key for HS256
    private static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60; // 5 hours in seconds

    // Generate JWT Token with claims (e.g., role, subject)
    public String generateToken(String studentId, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role); // Add user role to claims

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(studentId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000)) // Set expiration time
                .signWith(SECRET_KEY) // Sign with the secure key
                .compact();
    }

    // Retrieve a specific claim from the token (e.g., role, subject)
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // Retrieve all claims from the JWT token
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY) // Set the secure signing key
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Validate if the token is expired
    public Boolean isTokenExpired(String token) {
        final Date expiration = getClaimFromToken(token, Claims::getExpiration);
        return expiration.before(new Date());
    }

    // Validate the token by matching the user details
    public Boolean validateToken(String token, String studentId) {
        final String subject = getClaimFromToken(token, Claims::getSubject);
        return (subject.equals(studentId) && !isTokenExpired(token)); // Validate subject and check if token expired
    }
}
