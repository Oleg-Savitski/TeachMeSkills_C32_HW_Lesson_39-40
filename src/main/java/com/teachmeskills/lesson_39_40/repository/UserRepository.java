package com.teachmeskills.lesson_39_40.repository;

import com.teachmeskills.lesson_39_40.model.User;
import com.teachmeskills.lesson_39_40.utils.config.database.DatabaseConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.teachmeskills.lesson_39_40.utils.constants.SQLQuery.*;

@Repository
public class UserRepository {

    private final DatabaseConfig databaseConfig;
    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    @Autowired
    public UserRepository(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    public Boolean createUser(User user) {
        if (user == null) {
            logger.error("User  object is null");
            return false;
        }
        if (user.getFirstname() == null || user.getSecondName() == null || user.getEmail() == null || user.getTelephoneNumber() == null) {
            logger.error("User  fields cannot be null -> firstname={}, secondName={}, email={}, telephoneNumber={}",
                    user.getFirstname(), user.getSecondName(), user.getEmail(), user.getTelephoneNumber());
            return false;
        }

        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_IS_SAVE_USER, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, user.getFirstname());
            preparedStatement.setString(2, user.getSecondName());
            preparedStatement.setInt(3, user.getAge());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getSex());
            preparedStatement.setString(6, user.getTelephoneNumber());
            preparedStatement.setTimestamp(7, Timestamp.valueOf(user.getCreated().toLocalDateTime()));
            preparedStatement.setTimestamp(8, Timestamp.valueOf(user.getUpdated().toLocalDateTime()));
            preparedStatement.setBoolean(9, user.getIsDeleted());

            boolean isSaved = preparedStatement.executeUpdate() > 0;
            if (isSaved) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setId(generatedKeys.getLong(1));
                    }
                }
                logger.info("User  saved successfully -> {}", user);
            } else {
                logger.warn("User  not saved -> {}", user);
            }
            return isSaved;
        } catch (SQLException e) {
            logger.error("Error saving user -> {}: {}", user, e.getMessage());
            return false;
        }
    }

    public User getUserById(Long id) {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_GET_USER_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return mapRowToUser (resultSet);
            }
        } catch (SQLException e) {
            logger.error("Error retrieving user with id {}: {}", id, e.getMessage());
        }
        return null;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_GET_ALL_USERS);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                users.add(mapRowToUser (resultSet));
            }
        } catch (SQLException e) {
            logger.error("Error retrieving all users -> {}", e.getMessage());
        }
        return users;
    }

    public Boolean updateUser (User user) {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER)) {
            preparedStatement.setString(1, user.getFirstname());
            preparedStatement.setString(2, user.getSecondName());
            preparedStatement.setInt(3, user.getAge());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getSex());
            preparedStatement.setString(6, user.getTelephoneNumber());
            preparedStatement.setTimestamp(7, user.getUpdated());
            preparedStatement.setLong(8, user.getId());

            boolean isUpdated = preparedStatement.executeUpdate() > 0;
            if (isUpdated) {
                logger.info("User  updated successfully -> {}", user);
            } else {
                logger.warn("User  not found for update -> {}", user);
            }
            return isUpdated;
        } catch (SQLException e) {
            logger.error("Error updating user -> {}: {}", user, e.getMessage());
            return false;
        }
    }

    public Boolean deleteUser (Long id) {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER)) {
            preparedStatement.setLong(1, id);
            boolean isDeleted = preparedStatement.executeUpdate() > 0;
            if (isDeleted) {
                logger.info("User  deleted successfully with id -> {}", id);
            } else {
                logger.warn("User  not found for deletion with id -> {}", id);
            }
            return isDeleted;
        } catch (SQLException e) {
            logger.error("Error deleting user with id {}: {}", id, e.getMessage());
            return false;
        }
    }

    private User mapRowToUser (ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setFirstname(resultSet.getString("firstname"));
        user.setSecondName(resultSet.getString("second_name"));
        user.setAge(resultSet.getInt("age"));
        user.setEmail(resultSet.getString("email"));
        user.setSex(resultSet.getString("sex"));
        user.setTelephoneNumber(resultSet.getString("telephone_number"));
        user.setCreated(resultSet.getTimestamp("created"));
        user.setUpdated(resultSet.getTimestamp("updated"));
        user.setIsDeleted(resultSet.getBoolean("is_deleted"));
        return user;
    }
}