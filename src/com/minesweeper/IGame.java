package com.minesweeper;

public interface IGame {
    GameState getGameState();

    void setGameState(GameState gameState);

    void selectSquare(String input);

    boolean isGameWon();

    String toString();
}
