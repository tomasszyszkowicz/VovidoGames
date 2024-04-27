package com.games.fun.fun_games.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.games.fun.fun_games.service.RecordService;

import java.util.Map;

/**
 * Controller class for handling HTTP requests related to records.
 */
@RestController
@RequestMapping("/records")
public class RecordController {

    private final RecordService recordService;

    @Autowired
    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    /**
     * Retrieves all records.
     *
     * @return a map containing the records
     */
    @GetMapping
    public Map<String, Object> getRecords() {
        return recordService.getRecords();
    }

    /**
     * Retrieves records by username.
     *
     * @param username the username to filter the records
     * @return a map containing the records filtered by username
     */
    @GetMapping("/{username}")
    public Map<String, Object> getRecordsByUsername(@PathVariable String username) {
        return recordService.getRecordsByUsername(username);
    }
}

