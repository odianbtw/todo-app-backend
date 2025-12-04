package com.test.todo.core.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserAccount {
    private UUID id;
    private String username;
    private String email;
    private String password;
    @Builder.Default
    private UserRole role = UserRole.USER;
}
