package com.demo.rest.user.avatar.service;

import com.demo.rest.user.entity.User;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class AvatarService {
    public byte[] get(User user) {
        return user.getAvatar();
    }

    public void updateAvatar(User user, InputStream is) {
        try {
            byte[] avatar = is.readAllBytes();

            user.setAvatar(avatar);
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

    public void deleteAvatar(User user) {
        user.setAvatar(new byte[0]);
    }
}
