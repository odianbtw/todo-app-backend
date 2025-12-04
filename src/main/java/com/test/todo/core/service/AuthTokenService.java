package com.test.todo.core.service;

import com.test.todo.core.domain.model.UserAccount;
import com.test.todo.core.model.AuthToken;

public interface AuthTokenService extends TokenService {
    AuthToken generateByUserAccount(UserAccount account);
    AuthToken refreshToken(String refreshToken);
    UserAccount getUserAccountFromToken(String token);
}
