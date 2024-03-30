package com.games.fun.fun_games.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HtmlController {

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/players-ui")
    public String players() {
        return "players_sql_ui";
    }
}
