package com.vasactrl.services.implementations;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    final private SecretKey key;
    final private JwtParser parser;

    public JwtService() {
        this.key = Keys.hmacShaKeyFor("csmdcdsdfjmfgjfddhjfdfdfgfhtrrfeefrefgregrgerdklfmdh".getBytes());
        this.parser = Jwts.parser().setSigningKey(this.key).build();
    }

    public String generateToken(String userName) {
        JwtBuilder builder = Jwts.builder()
                .subject(userName)
                .issuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(15, ChronoUnit.MINUTES)))
                .signWith(key);

        return builder.compact();
    }

    public String getUserName(String token) {
        Claims claims = parser.parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public boolean validate(UserDetails user, String token) {
        Claims claims = parser.parseSignedClaims(token).getPayload();

        return claims.getExpiration().after(Date.from(Instant.now())) && user.getUsername().equals(claims.getSubject());
    }
}

