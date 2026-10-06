package com.rps.server.repository;

import com.rps.server.entity.Game;
import com.rps.server.entity.User;
import com.rps.server.repository.base.BaseRepo;

import java.util.List;
import java.util.Optional;

public interface GameRepository extends BaseRepo<Game, Long> {
    Optional<Game> findActiveByUser(User user);
    List<Game> findFinishedByUser(User user);
    List<Game> findAllFinished();
}
