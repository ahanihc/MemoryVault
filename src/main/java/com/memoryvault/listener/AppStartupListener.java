package com.memoryvault.listener;

import com.memoryvault.scheduler.CapsuleUnlockScheduler;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppStartupListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("MemoryVault scheduler started...");
        CapsuleUnlockScheduler.startScheduler();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("MemoryVault application stopped...");
    }
}