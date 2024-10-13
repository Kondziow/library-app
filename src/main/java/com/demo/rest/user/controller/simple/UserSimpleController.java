package com.demo.rest.user.controller.simple;

import com.demo.rest.component.DtoFunctionFactory;
import com.demo.rest.controller.servlet.exception.NotFoundException;
import com.demo.rest.user.controller.api.UserController;
import com.demo.rest.user.dto.GetUserResponse;
import com.demo.rest.user.dto.GetUsersResponse;
import com.demo.rest.user.entity.User;
import com.demo.rest.user.service.UserService;
import lombok.RequiredArgsConstructor;

import java.io.InputStream;
import java.util.UUID;

public class UserSimpleController implements UserController {
    private final UserService userService;
    private final DtoFunctionFactory factory;

    public UserSimpleController(UserService userService, DtoFunctionFactory factory) {
        this.userService = userService;
        this.factory = factory;
    }

    @Override
    public GetUsersResponse getUsers() {
        return factory.usersToResponse().apply(userService.findAll());
    }

    @Override
    public GetUserResponse getUser(UUID id) {
        return userService.findById(id)
                .map(factory.userToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public byte[] getAvatar(UUID id) {
        return userService.getAvatar(id);
    }

    @Override
    public void createAvatar(UUID id, byte[] avatar) {
        userService.createAvatar(id,avatar);
    }

    @Override
    public void putAvatar(UUID id, InputStream avatar) {
        userService.findById(id).ifPresentOrElse(
                entity -> userService.updateAvatar(id, avatar),
                () -> {
                    throw new NotFoundException();
                });
    }

    @Override
    public void deleteAvatar(UUID id) {
        userService.deleteAvatar(id);
    }
}
