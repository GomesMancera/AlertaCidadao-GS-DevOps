package br.com.alerta.api.security;

import br.com.alerta.api.domain.Perfil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-minutes:60}")
    private long accessMinutes;

    @Value("${jwt.refresh-days:7}")
    private long refreshDays;

    private SecretKey key() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String accessToken(String email, Perfil perfil) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(email)
                .claim("roles", List.of("ROLE_" + perfil.name()))
                .claim("tipo", "access")
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(accessMinutes, ChronoUnit.MINUTES)))
                .signWith(key())
                .compact();
    }

    public String refreshToken(String email, Perfil perfil) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(email)
                .claim("roles", List.of("ROLE_" + perfil.name()))
                .claim("tipo", "refresh")
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(refreshDays, ChronoUnit.DAYS)))
                .signWith(key())
                .compact();
    }

    public Claims parse(String token) {
        return Jwts.parser().verifyWith(key()).build().parseSignedClaims(token).getPayload();
    }
}
