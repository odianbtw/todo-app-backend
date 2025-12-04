package com.test.todo.core.port.out;

import com.test.todo.core.domain.model.UserAccount;

import java.util.Optional;
import java.util.UUID;

public interface UserDao {
    void createUserAccount(UserAccount userAccount);
    Optional<UserAccount> findByIdentifier(String identifier);
    Optional<UserAccount> findByCredentials(String identifier, String password);
    Optional<UserAccount> findById(UUID id);
    void update(UUID id, UserAccount userAccount);
}
