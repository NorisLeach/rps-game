package com.rps.server.service;

import com.rps.server.dto.LeaderboardEntryDTO;
import com.rps.server.dto.PlayResponseDTO;
import com.rps.server.dto.StatisticsDTO;

import java.util.List;

public interface GameService {
    PlayResponseDTO play(String username, String playerMoveStr);
    StatisticsDTO getStatistics(String username);
    List<LeaderboardEntryDTO> getLeaderboard();
}
