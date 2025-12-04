package com.test.todo.core.port.in;

import com.test.todo.core.domain.model.UserAccount;

import java.util.UUID;

public interface UserService {
    void createUserAccount(UserAccount userAccount);
    UserAccount findByIdentifier(String identifier);
    UserAccount findByCredentials(String identifier, String password);
    UserAccount findById(UUID id);
    void update(UUID userId, UserAccount userAccount);
    void changeUserPassword(UUID userId, String currentPassword, String newPassword);
}
