package com.demo.rest.user.repository.memory;

import com.demo.rest.datastore.DataStore;
import com.demo.rest.user.entity.User;
import com.demo.rest.user.repository.api.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserInMemoryRepository implements UserRepository {
    private final DataStore dataStore;

    public UserInMemoryRepository(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public List<User> findAll() {
        return dataStore.findAllUsers();
    }

    @Override
    public Optional<User> find(UUID id) {
        return this.dataStore.findAllUsers().stream()
                .filter((user) -> user.getId().equals(id))
                .findFirst();
    }

    @Override
    public void create(User entity) {
        dataStore.createUser(entity);
    }

    @Override
    public void update(User entity) {
        dataStore.updateUser(entity);
    }

    @Override
    public void delete(User entity) {
        dataStore.deleteUser(entity.getId());
    }

    public byte[] getAvatar(UUID uuid){
        return this.dataStore.getAvatar(uuid);
    }
    public void createAvatar(UUID uuid, byte[] avatar){
        this.dataStore.createAvatar(uuid,avatar);
    }
    public void updateAvatar(UUID uuid,byte[] avatar){
        this.dataStore.updateAvatar(uuid,avatar);
    }
    public void deleteAvatar(UUID uuid){
        this.dataStore.deleteAvatar(uuid);
    }
}
