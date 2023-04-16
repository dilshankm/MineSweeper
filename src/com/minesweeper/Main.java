
package com.minesweeper;

import com.minesweeper.ui.ConsoleInterface;

public class Main {

    public static void main(String[] args) {
        ConsoleInterface ui = new ConsoleInterface();
        while (true) {
            ui.displayWelcomeMessage();

            int gridSize = ui.getGridSize();
            int mineCount = ui.getNumberOfMines(gridSize);

            Game game = new Game(gridSize, mineCount);

            while (game.getGameState() == GameState.PLAYING) {
                ui.displayGame(game);

                String input = ui.getInputString(gridSize);
                game.selectSquare(input);

                ui.displayGameState(game);
            }
            ui.playAgainPrompt();
        }
    }
}
