package com.teachmeskills.lesson_39_40.services;

import com.teachmeskills.lesson_39_40.utils.config.database.DatabaseConfig;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Getter
@Component
public class SomeService {
    private final DatabaseConfig databaseConfig;

    @Autowired
    public SomeService(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }
}