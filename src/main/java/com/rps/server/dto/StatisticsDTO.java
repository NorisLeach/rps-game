package com.rps.server.dto;

public class StatisticsDTO {

    private int totalGamesPlayed;
    private int highestScore;

    public StatisticsDTO() {}

    public StatisticsDTO(int totalGamesPlayed, int highestScore) {
        this.totalGamesPlayed = totalGamesPlayed;
        this.highestScore = highestScore;
    }

    public int getTotalGamesPlayed() { return totalGamesPlayed; }
    public void setTotalGamesPlayed(int totalGamesPlayed) { this.totalGamesPlayed = totalGamesPlayed; }
    public int getHighestScore() { return highestScore; }
    public void setHighestScore(int highestScore) { this.highestScore = highestScore; }
}
