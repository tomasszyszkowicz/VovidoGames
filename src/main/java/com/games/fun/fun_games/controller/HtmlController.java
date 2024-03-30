package com.games.fun.fun_games.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.ui.Model;

@Controller
public class HtmlController {

    @GetMapping("/players-ui")
    public String players() {
        return "players_sql_ui";
    }

    @GetMapping("/pexeso-menu")
    public String pexesoMenu() {
        return "pexeso-menu";
    }

    @GetMapping("/pexeso-game")
    public String pexeso(@RequestParam(name = "difficulty", defaultValue = "easy") String difficulty, Model model) {
        model.addAttribute("difficulty", difficulty);
        return "pexeso";
    }

}
