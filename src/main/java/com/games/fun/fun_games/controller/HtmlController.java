package com.games.fun.fun_games.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.games.fun.fun_games.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import org.springframework.ui.Model;

/**
 * This class is a controller for handling HTML requests related to games.
 */
@Controller
public class HtmlController {

    @Autowired
    UserRepository userRepository;

    /**
     * Handles the request for the players UI page.
     *
     * @return the name of the players SQL UI template
     */
    @GetMapping("/players-ui")
    public String players(Model model) {
        model.addAttribute("username", getLoggedInUsername());
        return "players_sql_ui";
    }

    /**
     * Handles the request for the games UI page.
     *
     * @return the home page template
     */
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("username", getLoggedInUsername());
        model.addAttribute("email", getLoggedInUserEmail());
        return "main-menu";
    }

    /**
     * Handles the request for the game menu page.
     *
     * @return the name of the game menu template
     */
    @GetMapping("/game-menu")
    public String gameMenu(Model model) {
        model.addAttribute("username", getLoggedInUsername());
        return "game-menu";
    }

    /**
     * Handles the request for the leaderboards page.
     *
     * @return the name of the leaderboards template
     */
    @GetMapping("/leaderboards")
    public String leaderboards(Model model) {
        model.addAttribute("username", getLoggedInUsername());
        return "leaderboards";
    }

    /**
     * Handles the request for the pexeso menu page.
     *
     * @param model the model object to be populated with data
     * @return the name of the pexeso menu template
     */
    @GetMapping("/pexeso-menu")
    public String pexesoMenu(Model model) {
        model.addAttribute("username", getLoggedInUsername());
        return "pexeso-menu";
    }

    /**
     * Handles the request for the pexeso game page.
     *
     * @param difficulty the difficulty level of the game
     * @param model the model object to be populated with data
     * @return the name of the pexeso template
     */
    @GetMapping("/pexeso-game")
    public String pexeso(@RequestParam(name = "difficulty", defaultValue = "easy") String difficulty, Model model) {
        model.addAttribute("difficulty", difficulty);
        model.addAttribute("username", getLoggedInUsername());
        return "pexeso";
    }

    /**
     * Retrieves the logged in username.
     *
     * @return the logged in username
     */
    private String getLoggedInUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName(); // Get logged in username
    }

    /**
     * Retrieves the email of the logged-in user.
     *
     * @return the email of the logged-in user or null if not found.
     */
    private String getLoggedInUserEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(auth.getName()).getEmail();
    }
}
