package com.demo.rest.user.avatar.repository;

import com.demo.rest.datastore.DataStore;

import java.util.Optional;
import java.util.UUID;

public class AvatarRepository {
    private final DataStore dataStore;

    public AvatarRepository(DataStore dataStore) {
        this.dataStore = dataStore;
    }
    public Optional<byte[]> getAvatar(UUID id){
        return dataStore.getAvatar(id);
    }
    public void updateAvatar(UUID id,byte[] avatar){
        dataStore.updateAvatar(id,avatar);
    }
    public void deleteAvatar(UUID id){
         dataStore.deleteAvatar(id);
    }
}
