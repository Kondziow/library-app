package com.demo.rest.user.repository.api;

import com.demo.rest.repository.api.Repository;
import com.demo.rest.user.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends Repository<User, UUID> {
    public Optional<byte[]> getAvatar(UUID uuid);
    public void updateAvatar(UUID uuid,byte[] avatar);
    public boolean deleteAvatar(UUID uuid);
}