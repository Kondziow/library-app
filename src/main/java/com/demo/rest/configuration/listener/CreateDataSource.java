package com.demo.rest.configuration.listener;

import com.demo.rest.datastore.DataStore;
import com.demo.rest.serialization.CloningUtility;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.nio.file.Path;

@WebListener
public class CreateDataSource implements ServletContextListener {
    public CreateDataSource() {
    }

    public void contextInitialized(ServletContextEvent event) {
        String avatarDirectory = event.getServletContext().getInitParameter("pictureDir");
        event.getServletContext().setAttribute("dataSource", new DataStore(new CloningUtility(), avatarDirectory));
    }
}
