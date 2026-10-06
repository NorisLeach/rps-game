package com.rps.server.service;

import com.rps.server.dto.GameStateDTO;

public interface UserService {
    GameStateDTO getOrCreateUser(String username);
}