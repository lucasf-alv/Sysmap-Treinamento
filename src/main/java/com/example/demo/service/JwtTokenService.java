package com.example.demo.service;

import com.example.demo.Config.JwtProperties;
import com.example.demo.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtTokenService {

    private final JwtProperties jwtProperties;

    public String generateToken(Usuario usuario) {

        Instant now = Instant.now();
        Instant expiration =
                now.plusMillis(
                        Long.parseLong(jwtProperties.expirationsMs())
                );

        return Jwts.builder()
                .subject(usuario.getEmail())
                .claim(
                        "roles",
                        usuario.getPerfil().getNome().name()
                )
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiration))
                .signWith(
                        getSignKey(),
                        Jwts.SIG.HS384
                )
                .compact();
    }

    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getUsernameFromToken(String token) {
        return parseToken(token).getSubject();
    }

    private SecretKey getSignKey() {

        byte[] keyBytes =
                jwtProperties.secret()
                        .getBytes(StandardCharsets.UTF_8);

        if (keyBytes.length < 32) {
            throw new RuntimeException(
                    "A chave JWT deve ter pelo menos 32 caracteres."
            );
        }

        return Keys.hmacShaKeyFor(keyBytes);
    }
}