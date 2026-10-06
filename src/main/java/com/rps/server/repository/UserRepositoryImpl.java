package com.rps.server.repository;

import com.rps.server.entity.User;
import com.rps.server.repository.base.BaseRepoImpl;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl extends BaseRepoImpl<User, Long> implements UserRepository {

    public UserRepositoryImpl() {
        super(User.class);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        List<User> results = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.username = :username", User.class
        ).setParameter("username", username).getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
}
