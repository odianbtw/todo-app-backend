package com.test.todo.core.service;

import com.test.todo.core.domain.model.UserAccount;
import com.test.todo.core.domain.model.UserRole;
import com.test.todo.core.exception.UnauthorizedException;
import com.test.todo.core.model.AuthToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class JwtTokenService implements AuthTokenService {

    private final AccessTokenGenerator tokenGenerator;
    private final Key key;

    @Override
    public AuthToken generateByUserAccount(UserAccount account) {
        final var accessToken = tokenGenerator.generateAccessToken(account);
        final var refreshToken = tokenGenerator.generateRefreshToken(account);
        return new AuthToken(
                accessToken,
                refreshToken
        );
    }

    @Override
    public AuthToken refreshToken(String refreshToken) {
        Claims claims = parse(refreshToken);

        var user = UserAccount.builder()
                .id(UUID.fromString(claims.getSubject()))
                .role(UserRole.valueOf(claims.get("role", String.class)))
                .build();

        return new AuthToken(
                tokenGenerator.generateAccessToken(user),
                tokenGenerator.generateRefreshToken(user)
        );
    }

    @Override
    public UserAccount getUserAccountFromToken(String token) {
        Claims claims = parse(token);

        return UserAccount.builder()
                .id(UUID.fromString(claims.getSubject()))
                .role(UserRole.valueOf(claims.get("role", String.class)))
                .build();
    }

    private Claims parse(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException | IllegalArgumentException e) {
            throw new UnauthorizedException("Invalid authentication token.");
        }
    }

}
