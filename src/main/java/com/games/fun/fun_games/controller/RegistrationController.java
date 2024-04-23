package com.games.fun.fun_games.controller;

import com.games.fun.fun_games.dto.RegistrationUserDto;
import com.games.fun.fun_games.entity.User;
import com.games.fun.fun_games.service.UserService;
import com.games.fun.fun_games.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

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
        newUser.setProfilePictureURL("https://t4.ftcdn.net/jpg/00/64/67/63/360_F_64676383_LdbmhiNM6Ypzb3FM4PPuFP9rHe7ri8Ju.jpg");
        
        Set<Role> roles = new HashSet<>();
        roles.add(Role.ROLE_USER);  // Add ROLE_USER as default role
        
        newUser.setRoles(roles);

        userService.registerUser(newUser);

        redirectAttributes.addFlashAttribute("successMessage", "Registration successful. Please login.");

        return "redirect:/login"; // Redirect to login page on successful registration
    }
}

