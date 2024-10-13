package com.demo.rest.configuration.listener;

import com.demo.rest.datastore.DataStore;
import com.demo.rest.serialization.CloningUtility;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.nio.file.Path;

@WebListener
public class CreateDataSource implements ServletContextListener {
    public CreateDataSource() {
    }

    public void contextInitialized(ServletContextEvent event) {

        Path avatarDirectory = (Path) event.getServletContext().getAttribute("avatarDirectory");
        event.getServletContext().setAttribute("dataSource", new DataStore(new CloningUtility(), avatarDirectory));
    }
}
