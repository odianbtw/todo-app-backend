package com.test.todo.core.model;

public record AuthToken(
        String accessToken,
        String refreshToken
) {
}
