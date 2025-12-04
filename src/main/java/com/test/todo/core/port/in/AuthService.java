package com.test.todo.core.port.in;

import com.test.todo.core.model.AuthToken;

public interface AuthService {
    void createResetPasswordToken(String email);
    void recoverPassword(String recoveryToken, String newPassword);
    AuthToken login(String identifier, String password);
    AuthToken refreshToken(String refreshToken);
}
