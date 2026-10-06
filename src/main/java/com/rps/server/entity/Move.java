package com.rps.server.entity;

public enum Move {
    ROCK,
    PAPER,
    SCISSORS;


    public static int compare(Move player, Move server) {
        if (player == server) return 0;

        switch (player) {
            case ROCK:
                return server == Move.SCISSORS ? 1 : -1;
            case PAPER:
                return server == Move.ROCK ? 1 : -1;
            case SCISSORS:
                return server == Move.PAPER ? 1 : -1;
            default:
                throw new IllegalArgumentException("Miscare invalida");
        }
    }
}
