package com.teachmeskills.lesson_39_40.controller;

import com.teachmeskills.lesson_39_40.exception.AuthenticationException;
import com.teachmeskills.lesson_39_40.services.AuthenticationService;
import com.teachmeskills.lesson_39_40.utils.AuthenticationResult;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam("login") String login,
            @RequestParam("password") String password,
            Model model,
            HttpServletRequest request) {

        try {
            AuthenticationResult result = authenticationService.isAuthenticateUser (login, password);

            if (result.success()) {
                request.getSession().setAttribute("currentUser", login);
                return "redirect:/register/users";
            } else {
                model.addAttribute("error", result.message());
                model.addAttribute("login", login);
                return "login";
            }
        } catch (AuthenticationException e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }
}