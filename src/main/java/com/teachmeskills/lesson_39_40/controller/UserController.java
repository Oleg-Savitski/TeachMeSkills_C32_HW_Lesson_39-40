package com.teachmeskills.lesson_39_40.controller;

import com.teachmeskills.lesson_39_40.exception.UserNotFoundException;
import com.teachmeskills.lesson_39_40.model.User;
import com.teachmeskills.lesson_39_40.model.dto.UserEditDto;
import com.teachmeskills.lesson_39_40.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/register/users")
public class UserController {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getAllUsers(HttpServletRequest request, Model model) {
        String login = (String) request.getSession().getAttribute("currentUser");
        if (login == null) {
            return "redirect:/auth/login";
        }

        try {
            List<User> users = userService.getAllUsers(login);
            if (!users.isEmpty()) {
                model.addAttribute("users", users);
                return "users";
            } else {
                throw new UserNotFoundException("No users found for login -> " + login);
            }
        } catch (UserNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long userId, Model model) {
        logger.info("Attempting to edit user with ID -> {}", userId);
        try {
            UserEditDto dto = userService.getUserEditDtoById(userId);
            logger.info("User data retrieved -> {}", dto);
            model.addAttribute("userEditDto", dto);
            return "edit-user";
        } catch (UserNotFoundException e) {
            logger.error("User not found -> {}", userId);
            return "redirect:/register/users?error=not_found";
        }
    }

    @PostMapping("/update")
    public String updateUser(
            @Valid @ModelAttribute("userEditDto") UserEditDto dto,
            BindingResult result,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ) {
        String login = (String) request.getSession().getAttribute("currentUser");
        if (login == null) {
            return "redirect:/auth/login";
        }

        if (result.hasErrors()) {
            result.getFieldErrors().forEach(error ->
                    redirectAttributes.addFlashAttribute(
                            error.getField() + "Error",
                            error.getDefaultMessage()
                    )
            );
            return "redirect:/register/users/edit/" + dto.getId();
        }

        try {
            userService.updateUser(dto, login);
            redirectAttributes.addFlashAttribute("success", "User updated successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error updating user -> " + e.getMessage());
        }

        return "redirect:/register/users";
    }
}