package com.demo.rest.user.dto.function;

import com.demo.rest.user.dto.GetUsersResponse;
import com.demo.rest.user.entity.User;

import java.util.List;
import java.util.function.Function;

public class UsersToResponseFunction implements Function<List<User>, GetUsersResponse> {
    @Override
    public GetUsersResponse apply(List<User> entities) {
        return GetUsersResponse.builder()
                .users(entities.stream()
                        .map(user -> GetUsersResponse.User.builder()
                                .id(user.getId())
                                .username(user.getUsername())
                                .emailAddress(user.getEmailAddress())
                                .build())
                        .toList())
                .build();
    }
}
