package com.aihealthcare.aihealthcare.auth.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtProvider {

    private final Key key;
    private final long accessTokenValidityMs;

    public JwtProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-validity-ms:3600000}") long accessTokenValidityMs
    ) {
        // HS256은 최소 32바이트(=256bit) 이상 권장
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessTokenValidityMs = accessTokenValidityMs;
    }

    public String generateAccessToken(Long userId, String email) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + accessTokenValidityMs);

        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim("email", email)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public JwtClaims parseAndValidate(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            Long userId = Long.parseLong(claims.getSubject());
            String email = claims.get("email", String.class);

            return new JwtClaims(userId, email);

        } catch (ExpiredJwtException e) {
            log.warn("JWT expired: {}", e.getMessage());
            throw new JwtTokenException(JwtTokenError.TOKEN_EXPIRED, e);

        } catch (JwtException | IllegalArgumentException e) {
            // 서명 위조, 형태 오류, 지원하지 않는 토큰 등 대부분 여기로 들어옴
            log.warn("JWT invalid: {}", e.getMessage());
            throw new JwtTokenException(JwtTokenError.TOKEN_INVALID, e);
        }
    }
}
