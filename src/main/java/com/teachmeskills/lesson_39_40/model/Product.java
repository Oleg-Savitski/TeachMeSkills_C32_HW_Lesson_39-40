package com.teachmeskills.lesson_39_40.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Product {
    private Long id;
    private String name;
    private Double price;
    private Timestamp created;
    private Timestamp updated;
}