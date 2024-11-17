package com.demo.rest.user.avatar.controller.simple;

import com.demo.rest.user.avatar.controller.api.AvatarController;
import com.demo.rest.user.avatar.service.AvatarService;
import com.demo.rest.user.service.UserService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import lombok.extern.java.Log;

import java.io.InputStream;
import java.util.UUID;

@Path("")
@Log
public class AvatarSimpleController implements AvatarController {
    private final UserService userService;
    private final AvatarService avatarService;

    @Inject
    public AvatarSimpleController(UserService userService, AvatarService avatarService) {
        this.userService = userService;
        this.avatarService = avatarService;
    }

    @Override
    public byte[] getAvatar(UUID id) {
        return userService.find(id)
                .map(avatarService::get)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void putAvatar(UUID id, InputStream avatar) {
        userService.find(id).ifPresentOrElse(
                entity -> avatarService.updateAvatar(entity, avatar),
                () -> {
                    throw new NotFoundException();
                });
    }

    @Override
    public void deleteAvatar(UUID id) {
        userService.find(id).ifPresentOrElse(
                entity -> avatarService.deleteAvatar(entity),
                () -> {
                    throw new NotFoundException();
                });
    }
}
