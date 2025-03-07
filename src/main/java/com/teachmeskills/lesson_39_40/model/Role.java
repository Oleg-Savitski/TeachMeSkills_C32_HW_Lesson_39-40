package com.teachmeskills.lesson_39_40.model;

import lombok.Getter;

@Getter
public enum Role {
    USER("The average user"),
    ADMIN("Administrator"),
    MODERATOR("Moderator");

    private final String description;

    Role(String description) {
        this.description = description;
    }
}