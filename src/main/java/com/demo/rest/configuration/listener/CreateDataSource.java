package com.demo.rest.configuration.listener;

import com.demo.rest.datastore.DataStore;
import com.demo.rest.serialization.CloningUtility;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class CreateDataSource implements ServletContextListener {
    public CreateDataSource() {
    }

    public void contextInitialized(ServletContextEvent event) {
        event.getServletContext().setAttribute("dataSource", new DataStore(new CloningUtility()));
    }
}
