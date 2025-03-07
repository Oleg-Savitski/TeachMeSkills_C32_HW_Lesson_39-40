package com.teachmeskills.lesson_39_40.model.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductDto {

    @NotNull(message = "Product ID is required for update")
    private Long id;

    @NotBlank(message = "Product name cannot be empty")
    @Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters")
    @Pattern(regexp = "^[a-zA-Zа-яА-ЯёЁ0-9\\s\\-.,()]+$",
            message = "Product name contains invalid characters")
    private String name;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be at least 0.01")
    @Digits(integer = 6, fraction = 2, message = "Invalid price format")
    private BigDecimal price;
}
