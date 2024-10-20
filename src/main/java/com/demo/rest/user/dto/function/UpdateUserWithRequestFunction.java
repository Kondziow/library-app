package com.demo.rest.user.dto.function;

import com.demo.rest.user.dto.PatchUserRequest;
import com.demo.rest.user.entity.User;

import java.util.function.BiFunction;

public class UpdateUserWithRequestFunction implements BiFunction<User, PatchUserRequest, User> {
    @Override
    public User apply(User entity, PatchUserRequest request) {
        return User.builder()
                .id(entity.getId())
                .username(request.getUsername())
                .emailAddress(request.getEmailAddress())
                .build();
    }
}
