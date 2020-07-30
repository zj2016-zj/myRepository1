package com.zj.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyListenerComponent implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("servletContext创建了");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("servletContext销毁了");
    }
}
