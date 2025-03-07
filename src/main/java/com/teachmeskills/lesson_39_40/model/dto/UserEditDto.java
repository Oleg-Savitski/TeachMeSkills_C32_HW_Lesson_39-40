package com.teachmeskills.lesson_39_40.model.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEditDto {

    @NotNull(message = "User ID must not be null")
    private Long id;

    @NotBlank(message = "First name cannot be empty")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-zA-Zа-яА-ЯёЁ\\-\\s']+$",
            message = "First name contains invalid characters")
    private String firstname;

    @NotBlank(message = "Last name cannot be empty")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-zA-Zа-яА-ЯёЁ\\-\\s']+$",
            message = "Last name contains invalid characters")
    private String secondName;

    @NotNull(message = "Age is required")
    @Min(value = 12, message = "Age must be at least 12 years")
    @Max(value = 120, message = "Age cannot exceed 120 years")
    private Integer age;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Gender must be selected")
    @Pattern(regexp = "^(male|female)$",
            message = "Invalid gender value")
    private String sex;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[0-9\\-\\s()]{7,20}$",
            message = "Invalid phone number format")
    private String telephoneNumber;
}