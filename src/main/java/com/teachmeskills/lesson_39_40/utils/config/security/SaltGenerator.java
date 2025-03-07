package com.teachmeskills.lesson_39_40.utils.config.security;

import com.teachmeskills.lesson_39_40.exception.SaltGenerationException;

import java.security.SecureRandom;
import java.util.Base64;

public class SaltGenerator {

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final int SALT_LENGTH = 16;

    public static String generateSalt() {
        try {
            byte[] salt = new byte[SALT_LENGTH];
            secureRandom.nextBytes(salt);
            return Base64.getEncoder().encodeToString(salt);
        } catch (Exception e) {
            throw new SaltGenerationException("Error generating salt", e);
        }
    }
}