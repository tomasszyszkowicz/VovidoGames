package com.games.fun.fun_games.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * This class is a controller for handling login and logout operations.
 */
@Controller
public class LoginController {

    /**
     * Handles the GET request for "/login" endpoint.
     * @return the name of the login view template.
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * Handles the GET request for "/logout" endpoint.
     * Invalidates the current user's session and redirects to the login page with a logout message.
     * @param request the HTTP servlet request.
     * @param response the HTTP servlet response.
     * @return the redirect URL to the login page with a logout message.
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }
    
}
