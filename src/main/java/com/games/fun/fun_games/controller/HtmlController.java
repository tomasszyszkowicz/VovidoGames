package com.games.fun.fun_games.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
     * Handles the request for the root page.
     * 
     * @return the name of the login template
     */
    @GetMapping("/")
    public String root() {
        return "login";
    }

    /**
     * Handles the request for the players UI page.
     *  
     * @param model the model object to be populated with data
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
     * @param model the model object to be populated with data
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
     * @param model the model object to be populated with data
     * @return the name of the game menu template
     */
    @GetMapping("/game-menu")
    public String gameMenu(Model model) {
        model.addAttribute("username", getLoggedInUsername());
        model.addAttribute("email", getLoggedInUserEmail());
        return "game-menu";
    }

    /**
     * Handles the request for the leaderboards page.
     * 
     * @param model the model object to be populated with data
     * @return the name of the leaderboards template
     */
    @GetMapping("/leaderboards")
    public String leaderboards(Model model) {
        model.addAttribute("username", getLoggedInUsername());
        model.addAttribute("email", getLoggedInUserEmail());
        return "leaderboards";
    }

    /**
     * Handles the request for the forum page.
     * 
     * @param model the model object to be populated with data
     * @param bottom the bottom index of the posts to be displayed
     * @param top the top index of the posts to be displayed
     * @return the name of the forum template
     */
    @GetMapping("/forum")
    public String forum(Model model,
                        @RequestParam(defaultValue = "0") int bottom,
                        @RequestParam(defaultValue = "9") int top) {
        model.addAttribute("username", getLoggedInUsername());
        model.addAttribute("email", getLoggedInUserEmail());
        return "forum";
    }

    /**
     * Handles the request for the post page.
     * 
     * @param model the model object to be populated with data
     * @param id the id of the post to be displayed
     * @param bottom the bottom index of the comments to be displayed
     * @param top the top index of the comments to be displayed
     * @return the name of the post template
     */
    @GetMapping("/post/{id}")
    public String post(Model model, 
                        @PathVariable Long id,
                        @RequestParam(defaultValue = "0") int bottom,
                        @RequestParam(defaultValue = "9") int top) {
        model.addAttribute("username", getLoggedInUsername());
        model.addAttribute("email", getLoggedInUserEmail());
        model.addAttribute("postId", id);
        return "post";
    }

    /**
     * Handles the request for the profile page.
     * 
     * @param model the model object to be populated with data
     * @param username the username of the profile to be displayed
     * @return the name of the profile template
     */
    @GetMapping("profile/{username}")
    public String profile(Model model, @PathVariable String username) {
        model.addAttribute("username", getLoggedInUsername());
        model.addAttribute("email", getLoggedInUserEmail());
        model.addAttribute("profileUsername", username);
        return "profile";
    }

    /**
     * Handles the request for the settings page.
     * 
     * @param model the model object to be populated with data
     * @return the name of the settings template
     */
    @GetMapping("settings")
    public String settings(Model model) {
        model.addAttribute("username", getLoggedInUsername());
        model.addAttribute("email", getLoggedInUserEmail());
        return "settings";
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
        model.addAttribute("email", getLoggedInUserEmail());
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
        model.addAttribute("email", getLoggedInUserEmail());
        return "pexeso";
    }

    /**
     * Handles the request for the snake game page.
     *
     * @param model the model object to be populated with data
     * @return the name of the snake template
     */
    @GetMapping("/snake-game")
    public String snake(Model model) {
        model.addAttribute("username", getLoggedInUsername());
        model.addAttribute("email", getLoggedInUserEmail());
        return "snake";
    }

    /**
     * Handles the request for the jump game page.
     *
     * @param model the model object to be populated with data
     * @return the name of the jump template
     */
    @GetMapping("/jump-game")
    public String jump(Model model) {
        model.addAttribute("username", getLoggedInUsername());
        model.addAttribute("email", getLoggedInUserEmail());
        return "jump";
    }

    /**
     * Handles the request for the admin menu page.
     *
     * @param model the model object to be populated with data
     * @return the name of the admin menu template
     */
    @GetMapping("/admin/admin-menu")
    public String adminMenu(Model model) {
        model.addAttribute("username", getLoggedInUsername());
        model.addAttribute("email", getLoggedInUserEmail());
        return "admin-menu";
    }

    /**
     * Handles the request for the admin users page.
     *
     * @param model the model object to be populated with data
     * @return the name of the admin users template
     */
    @GetMapping("/access-denied")
    public String accessDenied(Model model) {
        model.addAttribute("username", getLoggedInUsername());
        model.addAttribute("email", getLoggedInUserEmail());
        return "access-denied";
    }

    /**
     * Handles the request for the error page.
     *
     * @param model the model object to be populated with data
     * @return the name of the error template
     */
    @GetMapping("/error")
    public String error(Model model) {
        model.addAttribute("username", getLoggedInUsername());
        model.addAttribute("email", getLoggedInUserEmail());
        return "error";
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
