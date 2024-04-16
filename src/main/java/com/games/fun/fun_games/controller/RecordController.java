package com.games.fun.fun_games.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.games.fun.fun_games.service.RecordService;

@RestController
@RequestMapping("/records")
public class RecordController {

    private final RecordService recordService;

    @Autowired
    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @GetMapping("/{username}")
    public String getRecordsByUsername(@PathVariable String username) {
        return recordService.getRecordsByUsername(username);
    }



    
}

