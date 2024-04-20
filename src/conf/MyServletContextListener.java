package conf;

import db.DatabaseInitializationExecutor;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import scheduler.ReminderScheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MyServletContextListener implements ServletContextListener {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Override
    public void contextInitialized(ServletContextEvent contextEvent) {
        new DatabaseInitializationExecutor().run();

        scheduler.scheduleAtFixedRate(() -> new ReminderScheduler().sendEmailReminders(), 10, 60, TimeUnit.SECONDS);

        System.out.println("MyServletContextListener contextInitialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        scheduler.shutdown();
        ServletContextListener.super.contextDestroyed(sce);
        System.out.println("MyServletContextListener contextDestroyed");
    }
}
