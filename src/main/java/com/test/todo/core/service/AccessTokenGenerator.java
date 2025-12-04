package com.test.todo.core.service;

import com.test.todo.core.domain.model.UserAccount;

public interface AccessTokenGenerator {
    String generateAccessToken(UserAccount account);
    String generateRefreshToken(UserAccount account);
}
