package com.demo.rest.user.avatar.controller.simple;

import com.demo.rest.controller.servlet.exception.NotFoundException;
import com.demo.rest.user.avatar.controller.api.AvatarController;
import com.demo.rest.user.avatar.service.AvatarService;
import com.demo.rest.user.service.UserService;

import java.io.InputStream;
import java.util.UUID;

public class AvatarSimpleController implements AvatarController {
    private final UserService userService;
    private final AvatarService avatarService;

    public AvatarSimpleController(UserService userService, AvatarService avatarService) {
        this.userService = userService;
        this.avatarService = avatarService;
    }
    @Override
    public byte[] getAvatar(UUID id) {
        return avatarService.getAvatar(id)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void putAvatar(UUID id, InputStream avatar) {
        userService.findById(id).ifPresentOrElse(
                entity -> avatarService.updateAvatar(id, avatar),
                () -> {
                    throw new NotFoundException();
                });
    }

    @Override
    public void deleteAvatar(UUID id) {
        if(!avatarService.deleteAvatar(id)) {
            throw new NotFoundException();
        }
    }
}