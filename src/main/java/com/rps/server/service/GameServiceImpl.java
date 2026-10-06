package com.rps.server.service;

import com.rps.server.dto.LeaderboardEntryDTO;
import com.rps.server.dto.PlayResponseDTO;
import com.rps.server.dto.StatisticsDTO;
import com.rps.server.entity.Game;
import com.rps.server.entity.Move;
import com.rps.server.entity.User;
import com.rps.server.repository.GameRepository;
import com.rps.server.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    private static final Logger logger = LogManager.getLogger(GameServiceImpl.class);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    public GameServiceImpl(UserRepository userRepository, GameRepository gameRepository) {
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public PlayResponseDTO play(String username, String playerMoveStr) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Userul " + username + " nu exista!"));

        Game game = gameRepository.findActiveByUser(user)
                .orElseGet(() -> gameRepository.save(new Game(user)));

        Move playerMove = Move.valueOf(playerMoveStr.toUpperCase());

        Move[] allMoves = Move.values();
        Move serverMove = allMoves[new Random().nextInt(allMoves.length)];

        int outcome = Move.compare(playerMove, serverMove);

        String result;
        boolean gameOver = false;

        if (outcome == 1) {
            result = "WIN";
            game.setScore(game.getScore() + 1);
            gameRepository.save(game);
            logger.info("WIN: user={} playerMove={} serverMove={} scor nou={}", username, playerMove, serverMove, game.getScore());

        } else if (outcome == 0) {
            result = "DRAW";
            game.setDrawCount(game.getDrawCount() + 1);
            gameRepository.save(game);
            logger.info("DRAW: user={} playerMove={} serverMove={} draws={}", username, playerMove, serverMove, game.getDrawCount());

        } else {
            result = "LOSS";
            game.setFinished(true);
            game.setTimestamp(LocalDateTime.now());
            gameRepository.save(game);

            Game newGame = new Game(user);
            gameRepository.save(newGame);
            gameOver = true;

            logger.info("LOSS: user={} playerMove={} serverMove={} - joc finalizat cu scor={}, draws={}. Joc nou pornit.",
                    username, playerMove, serverMove, game.getScore(), game.getDrawCount());

            return new PlayResponseDTO(
                    playerMove.name(), serverMove.name(), result,
                    newGame.getScore(), newGame.getDrawCount(), gameOver
            );
        }

        return new PlayResponseDTO(
                playerMove.name(), serverMove.name(), result,
                game.getScore(), game.getDrawCount(), gameOver
        );
    }

    @Override
    public StatisticsDTO getStatistics(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Userul " + username + " nu exista!"));

        List<Game> finishedGames = gameRepository.findFinishedByUser(user);

        int total = finishedGames.size();
        int highestScore = finishedGames.stream()
                .mapToInt(Game::getScore)
                .max()
                .orElse(0);

        logger.info("Statistici pentru {}: total={}, highestScore={}", username, total, highestScore);
        return new StatisticsDTO(total, highestScore);
    }

    @Override
    public List<LeaderboardEntryDTO> getLeaderboard() {
        List<Game> finishedGames = gameRepository.findAllFinished();

        List<LeaderboardEntryDTO> leaderboard = finishedGames.stream()
                .map(g -> new LeaderboardEntryDTO(
                        g.getUser().getUsername(),
                        g.getScore(),
                        g.getDrawCount(),
                        g.getTimestamp() != null ? g.getTimestamp().format(FORMATTER) : "N/A"
                ))
                .collect(Collectors.toList());

        logger.info("Leaderboard generat cu {} intrari", leaderboard.size());
        return leaderboard;
    }
}
