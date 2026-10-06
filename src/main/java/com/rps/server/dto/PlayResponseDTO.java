package com.rps.server.dto;

public class PlayResponseDTO {

    private String playerMove;
    private String serverMove;
    private String result;
    private int score;
    private int drawCount;
    private boolean gameOver;

    public PlayResponseDTO() {}

    public PlayResponseDTO(String playerMove, String serverMove, String result,
                           int score, int drawCount, boolean gameOver) {
        this.playerMove = playerMove;
        this.serverMove = serverMove;
        this.result = result;
        this.score = score;
        this.drawCount = drawCount;
        this.gameOver = gameOver;
    }

    public String getPlayerMove() { return playerMove; }
    public void setPlayerMove(String playerMove) { this.playerMove = playerMove; }
    public String getServerMove() { return serverMove; }
    public void setServerMove(String serverMove) { this.serverMove = serverMove; }
    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
    public int getDrawCount() { return drawCount; }
    public void setDrawCount(int drawCount) { this.drawCount = drawCount; }
    public boolean isGameOver() { return gameOver; }
    public void setGameOver(boolean gameOver) { this.gameOver = gameOver; }
}
