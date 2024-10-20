package com.demo.rest.user.controller.simple;

import com.demo.rest.component.DtoFunctionFactory;
import com.demo.rest.controller.servlet.exception.BadRequestException;
import com.demo.rest.controller.servlet.exception.NotFoundException;
import com.demo.rest.user.controller.api.UserController;
import com.demo.rest.user.dto.GetUserResponse;
import com.demo.rest.user.dto.GetUsersResponse;
import com.demo.rest.user.dto.PatchUserRequest;
import com.demo.rest.user.dto.PutUserRequest;
import com.demo.rest.user.service.UserService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.UUID;

@RequestScoped
public class UserSimpleController implements UserController {
    private final UserService service;
    private final DtoFunctionFactory factory;

    @Inject
    public UserSimpleController(UserService service, DtoFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    public GetUsersResponse getUsers() {
        return factory.usersToResponse().apply(service.findAll());
    }

    @Override
    public GetUserResponse getUser(UUID id) {
        return service.findById(id)
                .map(factory.userToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void putUser(UUID id, PutUserRequest request) {
        try {
            service.create(factory.requestToUser().apply(id, request));
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }

    @Override
    public void updateUser(UUID id, PatchUserRequest request) {
        service.findById(id).ifPresentOrElse(
                entity -> service.update(factory.updateUser().apply(entity, request)),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void deleteUser(UUID id) {
        service.findById(id).ifPresentOrElse(
                entity -> service.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
