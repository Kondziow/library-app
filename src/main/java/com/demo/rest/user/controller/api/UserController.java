package com.demo.rest.user.controller.api;

import com.demo.rest.user.dto.GetUserResponse;
import com.demo.rest.user.dto.GetUsersResponse;
import com.demo.rest.user.dto.PatchUserRequest;
import com.demo.rest.user.dto.PutUserRequest;

import java.util.UUID;

public interface UserController {
    GetUsersResponse getUsers();
    GetUserResponse getUser(UUID id);
    void putUser(UUID id, PutUserRequest request);
    void updateUser(UUID id, PatchUserRequest request);
    void deleteUser(UUID id);
}
