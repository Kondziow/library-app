package com.demo.rest.configuration.listener;

import com.demo.rest.datastore.DataStore;
import com.demo.rest.user.repository.api.UserRepository;
import com.demo.rest.user.repository.memory.UserInMemoryRepository;
import com.demo.rest.user.service.UserService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class CreateServices implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        DataStore dataStore = (DataStore) event.getServletContext().getAttribute("dataSource");

        UserRepository userRepository = new UserInMemoryRepository(dataStore);

        event.getServletContext().setAttribute("userService", new UserService(userRepository));
    }
}
