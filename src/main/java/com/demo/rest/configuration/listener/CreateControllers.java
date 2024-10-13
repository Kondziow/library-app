package com.demo.rest.configuration.listener;

import com.demo.rest.component.DtoFunctionFactory;
import com.demo.rest.user.controller.simple.UserSimpleController;
import com.demo.rest.user.service.UserService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class CreateControllers implements ServletContextListener {
    public void contextInitialized(ServletContextEvent event) {
        UserService userService = (UserService) event.getServletContext().getAttribute("userService");
        event.getServletContext().setAttribute(
                "userController",
                new UserSimpleController(
                        userService,
                        new DtoFunctionFactory()
                )
        );
    }
}
