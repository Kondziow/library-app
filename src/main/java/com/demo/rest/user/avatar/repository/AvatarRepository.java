package com.demo.rest.user.avatar.repository;

import com.demo.rest.datastore.DataStore;

import java.util.Optional;
import java.util.UUID;

public class AvatarRepository {
    private final DataStore dataStore;

    public AvatarRepository(DataStore dataStore) {
        this.dataStore = dataStore;
    }
    public Optional<byte[]> getAvatar(UUID uuid){
        return dataStore.getAvatar(uuid);
    }
    public void updateAvatar(UUID uuid,byte[] avatar){
        dataStore.updateAvatar(uuid,avatar);
    }
    public boolean deleteAvatar(UUID uuid){
        return dataStore.deleteAvatar(uuid);
    }
}
