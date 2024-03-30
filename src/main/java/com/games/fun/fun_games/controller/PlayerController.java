package com.games.fun.fun_games.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import com.games.fun.fun_games.entity.Player;
import com.games.fun.fun_games.repository.PlayerRepository;

/**
 * The PlayerController class handles HTTP requests related to players.
 */
@RestController
@RequestMapping("/players")
public class PlayerController {

    @Autowired
    private PlayerRepository playerRepository;

    /**
     * Retrieves all players.
     *
     * @return ResponseEntity containing a list of players and HTTP status code OK.
     */
    @GetMapping
    public ResponseEntity<List<Player>> getAllPlayers() {
        List<Player> players = playerRepository.findAll();
        return new ResponseEntity<>(players, HttpStatus.OK);
    }

    /**
     * Creates a new player.
     *
     * @param player The player object to be created.
     * @return ResponseEntity containing the created player and HTTP status code CREATED.
     */
    @PostMapping
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
        Player newPlayer = playerRepository.save(player);
        return new ResponseEntity<>(newPlayer, HttpStatus.CREATED);
    }

    /**
     * Updates an existing player.
     *
     * @param id        The ID of the player to be updated.
     * @param newPlayer The updated player object.
     * @return ResponseEntity containing the updated player and HTTP status code OK if the player exists,
     *         or ResponseEntity with HTTP status code NOT_FOUND if the player does not exist.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Player> updatePlayer(@PathVariable Long id, @RequestBody Player newPlayer) {
        if (playerRepository.existsById(id)) {
            Player existingPlayer = playerRepository.findById(id).get();
            existingPlayer.setUserName(newPlayer.getUserName());
            playerRepository.save(existingPlayer);
            return new ResponseEntity<>(existingPlayer, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Deletes a player.
     *
     * @param id The ID of the player to be deleted.
     * @return ResponseEntity with HTTP status code NO_CONTENT if the player exists,
     *         or ResponseEntity with HTTP status code NOT_FOUND if the player does not exist.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Player> deletePlayer(@PathVariable Long id) {
        if (playerRepository.existsById(id)) {
            playerRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.notFound().build();
    }
}
