package com.rps.server.dto;

public class LeaderboardEntryDTO {

    private String username;
    private int finalScore;
    private int drawCount;
    private String timestamp;

    public LeaderboardEntryDTO() {}

    public LeaderboardEntryDTO(String username, int finalScore, int drawCount, String timestamp) {
        this.username = username;
        this.finalScore = finalScore;
        this.drawCount = drawCount;
        this.timestamp = timestamp;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public int getFinalScore() { return finalScore; }
    public void setFinalScore(int finalScore) { this.finalScore = finalScore; }
    public int getDrawCount() { return drawCount; }
    public void setDrawCount(int drawCount) { this.drawCount = drawCount; }
    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}
