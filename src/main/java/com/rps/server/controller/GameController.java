package com.rps.server.controller;

import com.rps.server.dto.GameStateDTO;
import com.rps.server.dto.LeaderboardEntryDTO;
import com.rps.server.dto.PlayResponseDTO;
import com.rps.server.dto.StatisticsDTO;
import com.rps.server.service.GameService;
import com.rps.server.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class GameController {

    private static final Logger logger = LogManager.getLogger(GameController.class);

    private final UserService userService;
    private final GameService gameService;

    public GameController(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }


    @PostMapping("/user")
    public ResponseEntity<?> getOrCreateUser(@RequestBody Map<String, String> body) {
        try {
            String username = body.get("username");
            logger.info("Get or create user: {}", username);
            GameStateDTO state = userService.getOrCreateUser(username);
            return ResponseEntity.ok(state);
        } catch (Exception e) {
            logger.error("Eroare get/create user: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/game/play")
    public ResponseEntity<?> play(@RequestBody Map<String, String> body) {
        try {
            String username = body.get("username");
            String move = body.get("move");
            logger.info("Play: user={} move={}", username, move);
            PlayResponseDTO response = gameService.play(username, move);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Eroare play: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/stats/{username}")
    public ResponseEntity<?> getStatistics(@PathVariable String username) {
        try {
            logger.info("Statistici pentru: {}", username);
            StatisticsDTO stats = gameService.getStatistics(username);
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            logger.error("Eroare statistici: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/leaderboard")
    public ResponseEntity<?> getLeaderboard() {
        try {
            logger.info("Leaderboard request");
            List<LeaderboardEntryDTO> leaderboard = gameService.getLeaderboard();
            return ResponseEntity.ok(leaderboard);
        } catch (Exception e) {
            logger.error("Eroare leaderboard: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}