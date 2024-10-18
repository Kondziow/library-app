package com.demo.rest.user.service;

import com.demo.rest.user.entity.User;
import com.demo.rest.user.repository.api.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findById(UUID id) { return userRepository.find(id);}

    public List<User> findAll() { return userRepository.findAll();}

    public void create(User user){ userRepository.create(user);}
}
