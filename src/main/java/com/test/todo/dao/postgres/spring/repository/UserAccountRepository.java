package com.test.todo.dao.postgres.spring.repository;

import com.test.todo.dao.postgres.model.UserAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserAccountRepository extends JpaRepository<UserAccountEntity, UUID> {
    @Query("from UserAccountEntity u where u.username = :identifier or u.email = :identifier")
    Optional<UserAccountEntity> findByIdentifier(@Param("identifier") String identifier);
}
