package com.demo.rest.user.controller.simple;

import com.demo.rest.component.DtoFunctionFactory;
import com.demo.rest.controller.servlet.exception.NotFoundException;
import com.demo.rest.user.controller.api.UserController;
import com.demo.rest.user.dto.GetUserResponse;
import com.demo.rest.user.dto.GetUsersResponse;
import com.demo.rest.user.service.UserService;

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
}
