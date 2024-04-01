package com.games.fun.fun_games.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.games.fun.fun_games.repository.UserRepository;
import com.games.fun.fun_games.entity.User;
import com.games.fun.fun_games.dto.RegistrationUserDto;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

/**
 * Controller class for handling user registration.
 */
@Controller
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * GET request handler for displaying the registration form.
     * 
     * @param model the model object for passing data to the view
     * @return the name of the registration view template
     */
    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("registrationUserDto", new RegistrationUserDto());
        return "register";
    }

    /**
     * POST request handler for registering a new user account.
     * 
     * @param userDto             the registration user DTO object containing user data
     * @param result              the binding result object for validation errors
     * @param model               the model object for passing data to the view
     * @param redirectAttributes  the redirect attributes object for adding flash attributes
     * @return the name of the view template to redirect to
     */
    @PostMapping
    public String registerUserAccount(@ModelAttribute("registrationUserDto") RegistrationUserDto userDto,
                                      BindingResult result, Model model,
                                      RedirectAttributes redirectAttributes) {

        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            System.out.println(userDto.getPassword());
            System.out.println(userDto.getConfirmPassword());
            System.out.println(userDto.getEmail());
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
        userRepository.save(newUser);

        // Add a flash attribute to show a success message on the next page after redirect
        redirectAttributes.addFlashAttribute("successMessage", "Registration successful. Please login.");

        return "redirect:/login"; // Redirect to login page on successful registration
    }
}

