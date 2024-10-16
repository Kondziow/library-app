package com.demo.rest.user.avatar.controller.api;

import java.io.InputStream;
import java.util.UUID;

public interface AvatarController {
    byte[] getAvatar(UUID id);
    void putAvatar(UUID id, InputStream avatar);
    void deleteAvatar(UUID id);
}
