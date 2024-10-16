package com.demo.rest.datastore;

import com.demo.rest.serialization.CloningUtility;
import com.demo.rest.user.entity.User;
import jakarta.ws.rs.NotFoundException;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class DataStore {
    private final Path avatarPath;
    private final Set<User> users = new HashSet<>();
    private final CloningUtility cloningUtility;

    public DataStore(CloningUtility cloningUtility, String avatarPath) {
        this.avatarPath = Paths.get(avatarPath);
        this.cloningUtility = cloningUtility;
        System.out.println(avatarPath);

        try {
            Files.createDirectories(this.avatarPath);
            System.out.println("Directory created: " + this.avatarPath.toAbsolutePath());
        } catch (IOException e) {
            throw new IllegalStateException("Exception when creating picture directory", e);
        }
    }

    public List<User> findAllUsers() {
        return users.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public void createUser(User value) throws IllegalArgumentException {
        if (users.stream().anyMatch((character) -> character.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The user id \"%s\" is not unique".formatted(value.getId()));
        } else {
            users.add(cloningUtility.clone(value));
        }
    }

    public void updateUser(User value) throws IllegalArgumentException {
        if (users.removeIf((character) -> character.getId().equals(value.getId()))) {
            users.add(cloningUtility.clone(value));
        } else {
            throw new NotFoundException("The user with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    public void deleteUser(UUID id) {
        if (!users.removeIf(user -> user.getId().equals(id))) {
            throw new NotFoundException("The user with id \"%s\" does not exist".formatted(id));
        }
    }

    public Optional<byte[]> getAvatar(UUID uuid) {
        Path avatarPath = getAvatarPath(uuid);
        try {
            if (Files.exists(avatarPath)) {
                return Optional.of(Files.readAllBytes(avatarPath));
            } else {
                return Optional.empty();
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not get avatar with id \"%s\"".formatted(uuid), e);
        }
    }

    public void updateAvatar(UUID uuid, byte[] avatarData) {
        Path avatarPath = getAvatarPath(uuid);
        try {
            Files.write(avatarPath, avatarData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteAvatar(UUID uuid) {
        Path avatarPath = getAvatarPath(uuid);
        try {
            if (Files.exists(avatarPath)) {
                Files.delete(avatarPath);
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not delete avatar for id \"%s\"".formatted(uuid), e);
        }
    }

    public Path getAvatarPath(UUID userId) {
        return avatarPath.resolve(userId.toString() + ".png");
    }

}
