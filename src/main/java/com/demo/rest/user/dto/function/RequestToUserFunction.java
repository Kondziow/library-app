package com.demo.rest.user.dto.function;

import com.demo.rest.user.dto.PutUserRequest;
import com.demo.rest.user.entity.User;

import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToUserFunction implements BiFunction<UUID, PutUserRequest, User> {
    @Override
    public User apply(UUID id, PutUserRequest request) {
        return User.builder()
                .id(id)
                .username(request.getUsername())
                .emailAddress(request.getEmailAddress())
                .build();
    }
}
