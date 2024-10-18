package com.demo.rest.user.avatar.service;

import com.demo.rest.user.avatar.repository.AvatarRepository;
import com.demo.rest.user.repository.api.UserRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

public class AvatarService {
    private final UserRepository userRepository;
    private final AvatarRepository avatarRepository;

    public AvatarService(UserRepository userRepository, AvatarRepository avatarRepository) {
        this.userRepository = userRepository;
        this.avatarRepository = avatarRepository;
    }

    public Optional<byte[]> getAvatar(UUID id) {
        return avatarRepository.getAvatar(id);
    }

    public void updateAvatar(UUID id, InputStream is) {
        userRepository.find(id).ifPresent(user -> {
            try {
                byte[] avatar = is.readAllBytes();

                avatarRepository.updateAvatar(id, avatar);
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        });
    }

    public void deleteAvatar(UUID id) {
        avatarRepository.deleteAvatar(id);
    }
}
