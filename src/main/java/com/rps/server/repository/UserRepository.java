package com.rps.server.repository;

import com.rps.server.entity.User;
import com.rps.server.repository.base.BaseRepo;

import java.util.Optional;

public interface UserRepository extends BaseRepo<User, Long> {
    Optional<User> findByUsername(String username);
}
