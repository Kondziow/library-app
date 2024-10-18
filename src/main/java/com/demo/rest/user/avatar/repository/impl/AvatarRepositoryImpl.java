package com.demo.rest.user.avatar.repository.impl;

import com.demo.rest.datastore.DataStore;
import com.demo.rest.user.avatar.repository.api.AvatarRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class AvatarRepositoryImpl implements AvatarRepository {
    private final DataStore dataStore;

    @Inject
    public AvatarRepositoryImpl(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public Optional<byte[]> getAvatar(UUID id) {
        return dataStore.getAvatar(id);
    }

    public void updateAvatar(UUID id,byte[] avatar){
        dataStore.updateAvatar(id,avatar);
    }

    public void deleteAvatar(UUID id) {
         dataStore.deleteAvatar(id);
    }
}
