package com.demo.rest.user.service;

import com.demo.rest.user.entity.User;
import com.demo.rest.user.repository.api.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class UserService {
    private final UserRepository userRepository;

    @Inject
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findById(UUID id) { return userRepository.find(id);}

    public List<User> findAll() { return userRepository.findAll();}

    public void create(User user){ userRepository.create(user);}
}
