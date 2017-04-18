package com.malei.bot.configuration;

import com.malei.bot.entities.Notes;
import com.malei.bot.entities.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class HibernateConfig {
    private static  final SessionFactory sessionFactory;
    static {
        Properties prop= new Properties();
        prop.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/telegram_notes");
        prop.setProperty("hibernate.connection.username", "root");
        prop.setProperty("hibernate.connection.password", "pass");
        prop.setProperty("hibernate.show_sql", "true");
        prop.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");
        prop.setProperty("hibernate.c3p0.min_size", "7");
        prop.setProperty("hibernate.c3p0.max_size", "53");
        prop.setProperty("hibernate.c3p0.timeout", "100");
        prop.setProperty("hibernate.c3p0.max_statements", "50");
        prop.setProperty("hibernate.c3p0.idle_test_period", "1000");
        prop.setProperty("hibernate.c3p0.validate", "true");
        prop.setProperty("hibernate.current_session_context_class","thread");
      //  prop.setProperty("hibernate.hbm2ddl.auto","create");
        Configuration configuration = new Configuration();
        configuration.setProperties(prop);
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Notes.class);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
                configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
