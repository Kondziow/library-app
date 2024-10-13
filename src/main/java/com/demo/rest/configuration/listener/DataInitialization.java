package com.demo.rest.configuration.listener;

import com.demo.rest.user.entity.User;
import com.demo.rest.user.service.UserService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.util.UUID;

@WebListener
public class DataInitialization implements ServletContextListener {
    private UserService userService;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        userService = (UserService) event.getServletContext().getAttribute("userService");
        init();
    }

    private void init() {
        User Janek = User.builder()
                .id(UUID.fromString("67096e6b-5bf5-47c0-8d1d-f92c99a18858"))
                .username("Janek")
                .emailAddress("janek@gmail.com")
                .build();

        User Oskar = User.builder()
                .id(UUID.fromString("73a39da9-790f-415e-a4bd-1cf3aa641185"))
                .username("Oskar")
                .emailAddress("oskar@gmail.com")
                .build();

        User Michal = User.builder()
                .id(UUID.fromString("e6b416b3-5b6e-4303-b423-16ab1de3afc1"))
                .username("Michal")
                .emailAddress("Michal@gmail.com")
                .build();

        User Kacper = User.builder()
                .id(UUID.fromString("80ed5ffb-a67a-4253-910c-a64134a01ccf"))
                .username("Kacper")
                .emailAddress("kacper@gmail.com")
                .build();

        userService.create(Janek);
        userService.create(Oskar);
        userService.create(Michal);
        userService.create(Kacper);
    }
}
