package com.codecool.shop.config;

import com.codecool.shop.dao.implementation.DataManager;
import org.apache.log4j.BasicConfigurator;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Initializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            BasicConfigurator.configure();
            DataManager dataManager = new DataManager();
            dataManager.setup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        System.out.println("ServletContextListener destroyed");
    }
}
