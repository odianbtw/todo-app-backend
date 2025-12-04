package com.test.todo.dao.postgres.dao;

import com.test.todo.core.domain.model.UserAccount;
import com.test.todo.core.exception.DataConflictException;
import com.test.todo.core.exception.NotFoundException;
import com.test.todo.core.port.out.UserDao;
import com.test.todo.dao.postgres.mapper.UserEntityMapper;
import com.test.todo.dao.postgres.spring.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.time.Clock;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class DefaultUserDao implements UserDao {

    private final UserEntityMapper userMapper;
    private final UserAccountRepository userAccountRepository;
    private final Clock clock;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void createUserAccount(UserAccount userAccount) {
        final var user = userMapper.toUserAccountEntity(userAccount);
        final var encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setCreatedAt(clock.instant());
        user.setUpdatedAt(clock.instant());
        try {
            userAccountRepository.saveAndFlush(user);
        } catch (DataIntegrityViolationException e) {
            throw new DataConflictException("Some non-unique data were provided.");
        }
    }

    @Override
    public Optional<UserAccount> findByIdentifier(String identifier) {
        return userAccountRepository
                .findByIdentifier(identifier)
                .map(userMapper::toUserAccount);
    }

    @Override
    public Optional<UserAccount> findByCredentials(String identifier, String password) {
        final var user = userAccountRepository.findByIdentifier(identifier);
        if(user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return user.map(userMapper::toUserAccount);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserAccount> findById(UUID id) {
        return userAccountRepository
                .findById(id)
                .map(userMapper::toUserAccount);
    }

    @Override
    public void update(UUID id, UserAccount userAccount) {
        final var user = userAccountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Couldn't find user with provided id."));
        user.setUsername(userAccount.getUsername());
        user.setEmail(userAccount.getEmail());
        user.setUpdatedAt(clock.instant());
        try {
            userAccountRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new DataConflictException("Some non-unique data were provided.");
        }
    }
}
