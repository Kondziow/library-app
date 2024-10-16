package com.demo.rest.user.service;

import com.demo.rest.user.entity.User;
import com.demo.rest.user.repository.api.UserRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findById(UUID uuid) { return userRepository.find(uuid);}
    public List<User> findAll() { return userRepository.findAll();}
    public void create(User user){ userRepository.create(user);}


    public Optional<byte[]> getAvatar(UUID uuid) {
        return userRepository.getAvatar(uuid);
    }
    public void updateAvatar(UUID id, InputStream is) {
        userRepository.find(id).ifPresent(user -> {
            try {
                byte[] avatar = is.readAllBytes();

                userRepository.updateAvatar(id, avatar);
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        });
    }
    public boolean deleteAvatar(UUID uuid) {
        return userRepository.deleteAvatar(uuid);
    }
}
