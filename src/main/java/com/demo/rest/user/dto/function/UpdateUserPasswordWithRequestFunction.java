package com.demo.rest.user.dto.function;

import com.demo.rest.user.dto.PutPasswordRequest;
import com.demo.rest.user.entity.User;

import java.util.function.BiFunction;

public class UpdateUserPasswordWithRequestFunction implements BiFunction<User, PutPasswordRequest, User> {
    @Override
    public User apply(User entity, PutPasswordRequest request) {
        return User.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .emailAddress(entity.getEmailAddress())
                .password(request.getPassword())
                .build();
    }
}
