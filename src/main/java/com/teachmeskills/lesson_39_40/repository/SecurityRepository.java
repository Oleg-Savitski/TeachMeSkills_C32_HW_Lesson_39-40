package com.teachmeskills.lesson_39_40.repository;

import com.teachmeskills.lesson_39_40.model.Role;
import com.teachmeskills.lesson_39_40.model.Security;
import com.teachmeskills.lesson_39_40.utils.config.database.DatabaseConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;

import static com.teachmeskills.lesson_39_40.utils.constants.SQLQuery.INSERT_INTO_IS_SAVE_SECURITY;
import static com.teachmeskills.lesson_39_40.utils.constants.SQLQuery.SELECT_FROM_LOGIN_VALIDATE;

@Repository
public class SecurityRepository {

    private final DatabaseConfig databaseConfig;
    private static final Logger logger = LoggerFactory.getLogger(SecurityRepository.class);

    @Autowired
    public SecurityRepository(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    public Boolean saveSecurityUser(Security security) {
        if (security == null) {
            logger.error("Security object is null");
            return false;
        }
        if (security.getLogin() == null || security.getPassword() == null || security.getRole() == null || security.getSalt() == null) {
            logger.error("Security fields cannot be null -> login={}, password={}, role={}, salt={}",
                    security.getLogin(), security.getPassword(), security.getRole(), security.getSalt());
            return false;
        }

        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_IS_SAVE_SECURITY)) {

            preparedStatement.setString(1, security.getLogin());
            preparedStatement.setString(2, security.getPassword());
            preparedStatement.setString(3, security.getSalt());
            preparedStatement.setString(4, security.getRole().toString());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(security.getCreated().toLocalDateTime()));
            preparedStatement.setTimestamp(6, Timestamp.valueOf(security.getUpdated().toLocalDateTime()));
            preparedStatement.setLong(7, security.getUserId());

            boolean isSaved = preparedStatement.executeUpdate() > 0;
            if (isSaved) {
                logger.info("Security saved successfully -> {}", security);
            } else {
                logger.warn("Security not saved -> {}", security);
            }
            return isSaved;
        } catch (SQLException e) {
            logger.error("Error saving security -> {}: {}", security, e.getMessage());
            return false;
        }
    }

    public Security findByLoginValidate(String login) {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FROM_LOGIN_VALIDATE)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Security security = new Security();
                security.setId(resultSet.getLong("id"));
                security.setLogin(resultSet.getString("login"));
                security.setPassword(resultSet.getString("password"));
                security.setSalt(resultSet.getString("salt"));
                security.setRole(Role.valueOf(resultSet.getString("role")));
                security.setCreated(resultSet.getTimestamp("created"));
                security.setUpdated(resultSet.getTimestamp("updated"));
                security.setUserId(resultSet.getLong("user_id"));
                return security;
            }
        } catch (SQLException e) {
            logger.error("Error finding security by login -> {}: {}", login, e.getMessage());
        }
        return null;
    }
}