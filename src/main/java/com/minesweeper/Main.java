
package com.minesweeper;

import com.minesweeper.game.Game;
import com.minesweeper.game.GameState;
import com.minesweeper.ui.console.ConsoleInterface;

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
                int row = input.charAt(0) - 'A';
                int col = Integer.parseInt(input.substring(1)) - 1;
                game.selectSquare(input);
                if(game.getGameState() == GameState.PLAYING){
                    ui.displaySquareInfo(game, row, col);
                }
                ui.displayGameState(game);
            }
            ui.playAgainPrompt();
        }
    }
}
