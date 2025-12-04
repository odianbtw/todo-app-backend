package com.test.todo.core.port.out;

import com.test.todo.core.domain.model.UserAccount;

import java.util.UUID;


public interface UserIdentityDao {
    void createResetPasswordToken(UserAccount userAccount, String token);
    void changeUserPasswordViaToken(String token, String newPassword);
    void changeUserPassword(UUID userId, String currentPassword, String newPassword);
}
