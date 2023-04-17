package com.minesweeper.ui;

import com.minesweeper.game.Game;

public interface UserInterface {
    void displayWelcomeMessage();
    int getGridSize();
    int getNumberOfMines(int gridSize);
    void displayGame(Game game);
    String getInputString(int gridSize);
    void displayGameState(Game game);
    void playAgainPrompt();
}
