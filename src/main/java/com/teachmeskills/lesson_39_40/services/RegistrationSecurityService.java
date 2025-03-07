package com.teachmeskills.lesson_39_40.services;

import com.teachmeskills.lesson_39_40.exception.RegistrationException;
import com.teachmeskills.lesson_39_40.model.Role;
import com.teachmeskills.lesson_39_40.model.Security;
import com.teachmeskills.lesson_39_40.model.User;
import com.teachmeskills.lesson_39_40.repository.SecurityRepository;
import com.teachmeskills.lesson_39_40.repository.UserRepository;
import com.teachmeskills.lesson_39_40.utils.config.security.PasswordUtil;
import com.teachmeskills.lesson_39_40.utils.config.security.SaltGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class RegistrationSecurityService {

    private final UserRepository userRepository;
    private final SecurityRepository securityRepository;

    @Autowired
    public RegistrationSecurityService(UserRepository userRepository, SecurityRepository securityRepository) {
        this.userRepository = userRepository;
        this.securityRepository = securityRepository;
    }

    public Boolean registrationNewUser (String firstname, String secondName, Integer age, String email, String sex, String fullTelephoneNumber, String login, String password) {
        User user = new User();
        user.setFirstname(firstname);
        user.setSecondName(secondName);
        user.setAge(age);
        user.setEmail(email);
        user.setSex(sex);
        user.setTelephoneNumber(fullTelephoneNumber);
        user.setCreated(new Timestamp(System.currentTimeMillis()));
        user.setUpdated(new Timestamp(System.currentTimeMillis()));
        user.setIsDeleted(false);

        Boolean isUserSaved = userRepository.createUser (user);
        if (!isUserSaved) {
            throw new RegistrationException("Failed to save user during registration.");
        }

        Long userId = user.getId();

        Security security = new Security();
        security.setLogin(login);

        String salt = SaltGenerator.generateSalt();
        security.setPassword(PasswordUtil.hashPassword(password, salt));
        security.setSalt(salt);
        security.setRole(Role.USER);
        security.setCreated(new Timestamp(System.currentTimeMillis()));
        security.setUpdated(new Timestamp(System.currentTimeMillis()));
        security.setUserId(userId);

        if (!securityRepository.saveSecurityUser (security)) {
            throw new RegistrationException("Failed to save security information during registration.");
        }

        return true;
    }
}