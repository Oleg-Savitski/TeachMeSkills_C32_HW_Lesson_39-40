package com.teachmeskills.lesson_39_40.utils.config.database;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Getter
@Component
public final class DatabaseConfig {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);
    private static volatile DatabaseConfig instance;
    private String url;
    private String user;
    private String password;

    private DatabaseConfig() {
        loadProperties();
        checkDriver();
    }

    public static DatabaseConfig getInstance() {
        if (instance == null) {
            synchronized (DatabaseConfig.class) {
                if (instance == null) {
                    instance = new DatabaseConfig();
                }
            }
        }
        return instance;
    }

    private void loadProperties() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("database_config.properties")) {
            if (input == null) {
                logger.error("Sorry, unable to find database_config.properties");
                return;
            }

            Properties prop = new Properties();
            prop.load(input);
            url = prop.getProperty("db.url");
            user = prop.getProperty("db.user");
            password = prop.getProperty("db.password");

            logger.info("Database URL -> {}", url);
            logger.info("Database User -> {}", user);

            if (url == null || user == null || password == null) {
                logger.error("Database configuration properties are missing or invalid.");
            }
        } catch (IOException ex) {
            logger.error("Error loading configuration file -> {}", ex.getMessage(), ex);
        }
    }

    private void checkDriver() {
        try {
            Class.forName("org.postgresql.Driver");
            logger.info("PostgreSQL Driver Registered!");
        } catch (ClassNotFoundException e) {
            logger.error("PostgreSQL Driver not found.");
        }
    }

    public Connection getConnection() throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            logger.info("Database connection established successfully.");
            return connection;
        } catch (SQLException e) {
            logger.error("Error establishing database connection -> {}", e.getMessage());
            throw e;
        }
    }
}