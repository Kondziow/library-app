package com.demo.rest.user.service;

import com.demo.rest.user.entity.User;
import com.demo.rest.user.repository.api.UserRepository;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class UserService {
    private final UserRepository userRepository;

    @Inject
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> find(UUID id) { return userRepository.find(id);}
    public Optional<User> find(String username) { return userRepository.findByUsername(username);}

    public List<User> findAll() { return userRepository.findAll();}

    public void create(User user) { userRepository.create(user);}

    public void update(User user) { userRepository.update(user);}

    public void delete(UUID id) {userRepository.delete(userRepository.find(id).orElseThrow());}
}
