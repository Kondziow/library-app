package com.demo.rest.user.service;

import com.demo.rest.user.entity.User;
import com.demo.rest.user.entity.UserRoles;
import com.demo.rest.user.repository.api.UserRepository;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import lombok.NoArgsConstructor;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class UserService {
    private final UserRepository userRepository;
    private final Pbkdf2PasswordHash passwordHash;

    @Inject
    public UserService(UserRepository userRepository, Pbkdf2PasswordHash passwordHash) {
        this.userRepository = userRepository;
        this.passwordHash = passwordHash;
    }

    @RolesAllowed(UserRoles.ADMIN)
    public Optional<User> find(UUID id) {
        return userRepository.find(id);
    }

    @RolesAllowed(UserRoles.ADMIN)
    public Optional<User> find(String username) {
        return userRepository.findByUsername(username);
    }

    @RolesAllowed(UserRoles.ADMIN)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @PermitAll
    public void create(User user) throws NoSuchAlgorithmException, InvalidKeySpecException {
        user.setPassword(passwordHash.generate(user.getPassword().toCharArray()));
        userRepository.create(user);
    }

    @RolesAllowed(UserRoles.ADMIN)
    public void update(User user) {
        userRepository.update(user);
    }

    @RolesAllowed(UserRoles.ADMIN)
    public void delete(UUID id) {
        userRepository.delete(userRepository.find(id).orElseThrow());
    }

    @PermitAll
    public boolean verify(String login, String password) {
        return find(login)
                .map(user -> passwordHash.verify(password.toCharArray(), user.getPassword()))
                .orElse(false);
    }

//    @PermitAll
//    public void updateCallerPrincipalLastLoginDateTime() {
//        findCallerPrincipal().ifPresent(principal -> principal.setLastLoginDateTime(LocalDateTime.now()));
//    }
//
//    public Optional<User> findCallerPrincipal() {
//        if (securityContext.getCallerPrincipal() != null) {
//            return find(securityContext.getCallerPrincipal().getName());
//        } else {
//            return Optional.empty();
//        }
//    }
}
