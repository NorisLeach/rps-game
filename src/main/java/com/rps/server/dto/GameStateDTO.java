package com.rps.server.dto;

public class GameStateDTO {

    private String username;
    private int score;
    private int drawCount;

    public GameStateDTO() {}

    public GameStateDTO(String username, int score, int drawCount) {
        this.username = username;
        this.score = score;
        this.drawCount = drawCount;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
    public int getDrawCount() { return drawCount; }
    public void setDrawCount(int drawCount) { this.drawCount = drawCount; }
}
