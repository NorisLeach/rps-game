package com.rps.server.service;

import com.rps.server.dto.GameStateDTO;
import com.rps.server.entity.Game;
import com.rps.server.entity.User;
import com.rps.server.repository.GameRepository;
import com.rps.server.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    public UserServiceImpl(UserRepository userRepository, GameRepository gameRepository) {
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public GameStateDTO getOrCreateUser(String username) {
        User user = userRepository.findByUsername(username).orElseGet(() -> {
            logger.info("User nou creat: {}", username);
            return userRepository.save(new User(username));
        });

        Game activeGame = gameRepository.findActiveByUser(user).orElseGet(() -> {
            logger.info("Niciun joc activ pentru {}, pornim unul nou", username);
            return gameRepository.save(new Game(user));
        });

        logger.debug("Joc activ pentru {}: score={}, draws={}", username, activeGame.getScore(), activeGame.getDrawCount());
        return new GameStateDTO(username, activeGame.getScore(), activeGame.getDrawCount());
    }
}
