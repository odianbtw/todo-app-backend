package com.test.todo.api.controller;

import com.test.todo.api.mapper.UserDtoMapper;
import com.test.todo.api.model.ChangePasswordV1;
import com.test.todo.api.model.UpdateUserMeV1;
import com.test.todo.api.model.UserRepresentationV1;
import com.test.todo.api.service.AuthUtils;
import com.test.todo.core.port.in.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController implements UsersApi {

    private final UserService userService;
    private final UserDtoMapper userDtoMapper;

    @Override
    public ResponseEntity<UserRepresentationV1> getUserMe() {
        log.info("Started getting user itself.");
        final var userId = AuthUtils.getAuthenticatedUserId();
        final var user = userService.findById(userId);
        log.info("Finished getting user itself.");
        return ResponseEntity.ok(
                userDtoMapper.toUserRepresentationV1(user)
        );
    }

    @Override
    public ResponseEntity<Void> updateUserMe(UpdateUserMeV1 updateUserMeV1) {
        log.info("Started updating user itself.");
        final var userId = AuthUtils.getAuthenticatedUserId();
        userService.update(userId, userDtoMapper.toUserAccountUpdate(updateUserMeV1));
        log.info("Finished updating user itself.");
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @Override
    public ResponseEntity<Void> changeUserMePassword(ChangePasswordV1 changePasswordV1) {
        log.info("Started updating user's password.");
        final var userId = AuthUtils.getAuthenticatedUserId();
        userService.changeUserPassword(
                userId,
                changePasswordV1.getCurrentPassword(),
                changePasswordV1.getNewPassword()
        );
        log.info("Finished updating user's password.");
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
