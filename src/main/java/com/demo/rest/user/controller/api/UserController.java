package com.demo.rest.user.controller.api;

import com.demo.rest.user.dto.GetUserResponse;
import com.demo.rest.user.dto.GetUsersResponse;

import java.io.InputStream;
import java.util.UUID;

public interface UserController {
    GetUsersResponse getUsers();
    GetUserResponse getUser(UUID id);
    byte[] getAvatar(UUID id);
    void putAvatar(UUID id, InputStream avatar);
    void deleteAvatar(UUID id);
}
