package com.test.todo.core.service;

import com.test.todo.core.domain.model.UserAccount;
import com.test.todo.core.domain.model.UserRole;
import com.test.todo.core.exception.NotFoundException;
import com.test.todo.core.exception.UnauthorizedException;
import com.test.todo.core.port.in.UserService;
import com.test.todo.core.port.out.UserDao;
import com.test.todo.core.port.out.UserIdentityDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
@RequiredArgsConstructor
@Transactional
public class DefaultUserService implements UserService {

    private final IdGenerator idGenerator;
    private final UserDao userDao;
    private final UserIdentityDao userIdentityDao;

    @Override
    public void createUserAccount(UserAccount userAccount) {
        userAccount.setId(idGenerator.generateUuid());
        userAccount.setRole(UserRole.USER);
        userDao.createUserAccount(userAccount);
    }

    @Override
    public UserAccount findByIdentifier(String identifier) {
        return userDao.findByIdentifier(identifier)
                .orElseThrow(() -> new NotFoundException("User with provided identifier not found."));
    }

    @Override
    public UserAccount findByCredentials(String identifier, String password) {
        return userDao.findByCredentials(identifier, password)
                .orElseThrow(() -> new UnauthorizedException("Couldn't login via provided credentials."));
    }

    @Override
    public UserAccount findById(UUID id) {
        return userDao.findById(id)
                .orElseThrow(() -> new NotFoundException("User with provided id not found."));
    }

    @Override
    public void update(UUID userId, UserAccount userAccount) {
        userDao.update(userId, userAccount);
    }

    @Override
    public void changeUserPassword(UUID userId, String currentPassword, String newPassword) {
        userIdentityDao.changeUserPassword(userId, currentPassword, newPassword);
    }


}
