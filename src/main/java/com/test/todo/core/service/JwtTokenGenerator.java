package com.test.todo.core.service;

import com.test.todo.core.domain.model.UserAccount;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.sql.Date;
import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;


@Service
@RequiredArgsConstructor
public class JwtTokenGenerator implements AccessTokenGenerator {

    private final Key secret;
    private final Clock clock;

    @Override
    public String generateAccessToken(UserAccount userAccount) {
        return generate(userAccount, 3600);
    }

    @Override
    public String generateRefreshToken(UserAccount userAccount) {
        return generate(userAccount, 1209600);
    }

    private String generate(UserAccount userAccount, long expirationIn) {
        final Instant now = clock.instant();
        final Instant expiration = now.plus(expirationIn, ChronoUnit.SECONDS);
        return Jwts.builder()
                .setSubject(String.valueOf(userAccount.getId()))
                .claim("role", userAccount.getRole().toString())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiration))
                .signWith(secret,  SignatureAlgorithm.HS256)
                .compact();
    }
}
