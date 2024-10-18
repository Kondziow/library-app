package com.demo.rest.user.avatar.repository.api;

import com.demo.rest.datastore.DataStore;

import java.util.Optional;
import java.util.UUID;

public interface AvatarRepository {
    Optional<byte[]> getAvatar(UUID id);
    void updateAvatar(UUID id,byte[] avatar);
    void deleteAvatar(UUID id);
}
