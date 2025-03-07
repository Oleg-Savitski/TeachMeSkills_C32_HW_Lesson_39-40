package com.teachmeskills.lesson_39_40.controller;

import com.teachmeskills.lesson_39_40.exception.RegistrationException;
import com.teachmeskills.lesson_39_40.model.dto.RegistrationRequestDto;
import com.teachmeskills.lesson_39_40.services.RegistrationSecurityService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/security")
public class RegistrationSecurityController {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationSecurityController.class);
    private final RegistrationSecurityService securityService;

    @Autowired
    public RegistrationSecurityController(RegistrationSecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registrationRequestDto", new RegistrationRequestDto());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute @Valid RegistrationRequestDto requestDto,
                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                logger.error("Validation error -> {}", error.getDefaultMessage());
            }
            return "registration";
        }

        String fullTelephoneNumber = requestDto.getOperator() + requestDto.getTelephoneNumber();
        requestDto.setTelephoneNumber(fullTelephoneNumber);

        try {
            boolean isRegistered = securityService.registrationNewUser (
                    requestDto.getFirstname(),
                    requestDto.getSecondName(),
                    requestDto.getAge(),
                    requestDto.getEmail(),
                    requestDto.getSex(),
                    requestDto.getTelephoneNumber(),
                    requestDto.getLogin(),
                    requestDto.getPassword()
            );

            if (isRegistered) {
                return "login";
            } else {
                model.addAttribute("error", "Registration failed. Please try again.");
                return "registration";
            }
        } catch (RegistrationException e) {
            logger.error("Registration error -> {}", e.getMessage());
            model.addAttribute("error", e.getMessage());
            return "registration";
        }
    }
}