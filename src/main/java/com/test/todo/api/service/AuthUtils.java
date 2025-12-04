package com.test.todo.api.service;

import com.test.todo.core.domain.model.UserAccount;
import com.test.todo.core.exception.UnauthorizedException;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public class AuthUtils {

    private AuthUtils(){}

    public static UUID getAuthenticatedUserId(){
        final var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof UserAccount principal)) {
            throw new UnauthorizedException("Failed to authenticate request.");
        }
        if(principal.getId() == null)
            throw new UnauthorizedException("Failed to authenticate request.");
        return principal.getId();
    }

}
