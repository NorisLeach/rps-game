package com.rps.server.repository;

import com.rps.server.entity.Game;
import com.rps.server.entity.User;
import com.rps.server.repository.base.BaseRepoImpl;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class GameRepositoryImpl extends BaseRepoImpl<Game, Long> implements GameRepository {

    public GameRepositoryImpl() {
        super(Game.class);
    }

    @Override
    public Optional<Game> findActiveByUser(User user) {
        List<Game> results = entityManager.createQuery(
                "SELECT g FROM Game g WHERE g.user = :user AND g.finished = false", Game.class
        ).setParameter("user", user).getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public List<Game> findFinishedByUser(User user) {
        return entityManager.createQuery(
                "SELECT g FROM Game g WHERE g.user = :user AND g.finished = true", Game.class
        ).setParameter("user", user).getResultList();
    }

    @Override
    public List<Game> findAllFinished() {
        return entityManager.createQuery(
                "SELECT g FROM Game g WHERE g.finished = true " +
                "ORDER BY g.score DESC, g.drawCount DESC, g.timestamp DESC", Game.class
        ).getResultList();
    }
}
