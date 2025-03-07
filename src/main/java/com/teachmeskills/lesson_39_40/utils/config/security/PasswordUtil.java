package com.teachmeskills.lesson_39_40.utils.config.security;


import com.teachmeskills.lesson_39_40.exception.PasswordHashingException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PasswordUtil {

    public static String hashPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(Base64.getDecoder().decode(salt));
            byte[] hashedPassword = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new PasswordHashingException("Error hashing password", e);
        }
    }

    public static boolean checkPassword(String password, String salt, String hashed) {
        String hashedInputPassword = hashPassword(password, salt);
        return hashedInputPassword.equals(hashed);
    }
}