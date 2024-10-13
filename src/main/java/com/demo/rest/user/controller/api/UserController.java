package com.demo.rest.user.controller.api;

import com.demo.rest.user.dto.GetUserResponse;
import com.demo.rest.user.dto.GetUsersResponse;

import java.io.InputStream;
import java.util.UUID;

public interface UserController {
    GetUsersResponse getUsers();
    GetUserResponse getUser(UUID id);
    byte[] getUserAvatar(UUID id);
    void putUserAvatar(UUID id, InputStream avatar);
}
