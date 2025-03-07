package com.teachmeskills.lesson_39_40.services;

import com.teachmeskills.lesson_39_40.exception.UserNotFoundException;
import com.teachmeskills.lesson_39_40.model.Security;
import com.teachmeskills.lesson_39_40.model.User;
import com.teachmeskills.lesson_39_40.model.dto.UserEditDto;
import com.teachmeskills.lesson_39_40.repository.SecurityRepository;
import com.teachmeskills.lesson_39_40.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final SecurityRepository securityRepository;

    @Autowired
    public UserService(UserRepository userRepository, SecurityRepository securityRepository) {
        this.userRepository = userRepository;
        this.securityRepository = securityRepository;
    }

    public List<User> getAllUsers(String login) {
        logger.info("Fetching users for login -> {}", login);
        Security security = securityRepository.findByLoginValidate(login);

        if (security != null) {
            logger.info("User found with role -> {}", security.getRole());
            if ("MODERATOR".equals(security.getRole().toString())) {
                List<User> users = userRepository.getAllUsers().stream()
                        .sorted(Comparator.comparingLong(User::getId))
                        .collect(Collectors.toList());
                logger.info("Number of users fetched -> {}", users.size());
                return users;
            } else {
                logger.warn("User does not have MODERATOR role-> {}", login);
                throw new UserNotFoundException("User does not have MODERATOR role -> " + login);
            }
        } else {
            logger.warn("No security information found for login -> {}", login);
            throw new UserNotFoundException("No security information found for login -> " + login);
        }
    }

    public boolean createUser (User user, String login) {
        if (user == null) {
            logger.error("User object is null");
            throw new IllegalArgumentException("User object cannot be null");
        }
        if (user.getFirstname() == null || user.getSecondName() == null || user.getEmail() == null || user.getTelephoneNumber() == null) {
            logger.error("User fields cannot be null -> firstname={}, secondName={}, email={}, telephoneNumber={}",
                    user.getFirstname(), user.getSecondName(), user.getEmail(), user.getTelephoneNumber());
            throw new IllegalArgumentException("User fields cannot be null");
        }

        Security security = securityRepository.findByLoginValidate(login);
        if (security == null || !"MODERATOR".equals(security.getRole().toString())) {
            logger.warn("User does not have MODERATOR role -> {}", login);
            throw new SecurityException("User does not have MODERATOR role -> " + login);
        }

        user.setCreated(new Timestamp(System.currentTimeMillis()));
        user.setUpdated(new Timestamp(System.currentTimeMillis()));
        user.setIsDeleted(false);
        boolean isCreated = userRepository.createUser(user);
        logger.info("User created successfully -> {}", user);
        return isCreated;
    }

    public void updateUser(UserEditDto dto, String login) {
        if (dto == null || dto.getId() == null) {
            logger.error("User DTO or ID is null");
            throw new IllegalArgumentException("User DTO and ID cannot be null");
        }

        Security security = securityRepository.findByLoginValidate(login);
        if (security == null || !"MODERATOR".equals(security.getRole().toString())) {
            logger.warn("User {} doesn't have MODERATOR role", login);
            throw new SecurityException("Access denied");
        }

        User user = userRepository.getUserById(dto.getId());
        if (user == null) {
            logger.warn("User not found for update: {}", dto.getId());
            throw new UserNotFoundException("User not found");
        }

        updateUserFromDto(dto, user);
        user.setUpdated(new Timestamp(System.currentTimeMillis()));

        boolean isUpdated = userRepository.updateUser(user);
        if (!isUpdated) {
            logger.error("Update failed for user: {}", dto.getId());
            throw new IllegalStateException("User update failed");
        }
        logger.info("User updated successfully: {}", dto.getId());
    }

    private void updateUserFromDto(UserEditDto dto, User user) {
        user.setFirstname(dto.getFirstname());
        user.setSecondName(dto.getSecondName());
        user.setAge(dto.getAge());
        user.setEmail(dto.getEmail());
        user.setSex(dto.getSex());
        user.setTelephoneNumber(dto.getTelephoneNumber());
    }

    public void deleteUser (Long id, String login) {
        if (id == null) {
            logger.error("User ID is  null");
            throw new IllegalArgumentException("User ID cannot be null");
        }

        Security security = securityRepository.findByLoginValidate(login);
        if (security == null || !"MODERATOR".equals(security.getRole().toString())) {
            logger.warn("User does not  have MODERATOR role -> {}", login);
            throw new SecurityException("User does not have MODERATOR role -> " + login);
        }

        boolean isDeleted = userRepository.deleteUser (id);
        if (isDeleted) {
            logger.info("User deleted successfully with id -> {}", id);
        } else {
            logger.warn("User not found for deletion with id -> {}", id);
            throw new UserNotFoundException("User not found for deletion with id -> " + id);
        }
    }

    public User getUserById(Long id) {
        if (id == null) {
            logger.error("User ID is null");
            throw new IllegalArgumentException("User ID cannot be null");
        }

        User user = userRepository.getUserById(id);
        if (user == null) {
            logger.warn("User not found with id -> {}", id);
            throw new UserNotFoundException("User not found with id -> " + id);
        }
        return user;
    }

    public UserEditDto getUserEditDtoById(Long id) {
        User user = getUserById(id);
        return convertToDto(user);
    }

    private UserEditDto convertToDto(User user) {
        UserEditDto dto = new UserEditDto();
        dto.setId(user.getId());
        dto.setFirstname(user.getFirstname());
        dto.setSecondName(user.getSecondName());
        dto.setAge(user.getAge());
        dto.setEmail(user.getEmail());
        dto.setSex(user.getSex());
        dto.setTelephoneNumber(user.getTelephoneNumber());
        return dto;
    }
}