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
        return dataStore.findAllUsers().stream()
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
