package com.games.fun.fun_games.controller;

import com.games.fun.fun_games.dto.RegistrationUserDto;
import com.games.fun.fun_games.entity.User;
import com.games.fun.fun_games.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.crypto.password.PasswordEncoder;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("registrationUserDto", new RegistrationUserDto());
        return "register";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("registrationUserDto") RegistrationUserDto userDto,
                                      BindingResult result, Model model,
                                      RedirectAttributes redirectAttributes) {

        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            model.addAttribute("errorMessage", "Passwords do not match.");
            return "register"; // Return to registration page with error message
        }

        if (!(userDto.getPassword().length() > 6)) {
            model.addAttribute("errorMessage", "Password too short. Must be at least 6 characters.");
            return "register"; // Return to registration page with error message
        }

        User newUser = new User();
        newUser.setUsername(userDto.getUsername());
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        newUser.setEmail(userDto.getEmail());
        userService.registerUser(newUser);

        redirectAttributes.addFlashAttribute("successMessage", "Registration successful. Please login.");

        return "redirect:/login"; // Redirect to login page on successful registration
    }
}

