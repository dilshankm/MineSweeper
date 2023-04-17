package com.minesweeper.ui.console;

import com.minesweeper.constant.Constants;
import com.minesweeper.game.Game;
import com.minesweeper.game.GameState;
import com.minesweeper.messages.Messages;
import com.minesweeper.ui.UserInterface;
import com.minesweeper.utils.Utils;

import java.util.Scanner;

public class ConsoleInterface implements UserInterface {
    private Scanner scanner;

    public ConsoleInterface() {
        scanner = new Scanner(System.in);
    }

    public void displayWelcomeMessage() {
        System.out.println(Messages.WELCOME_MESSAGE);
        System.out.println();
    }

    public int getGridSize() {
        int gridSize;
        while (true) {
            System.out.print(Messages.GRID_MESSAGE);
            if (scanner.hasNextInt()) {
                gridSize = scanner.nextInt();
                if (gridSize >= Constants.GRID_MIN_SIZE && gridSize <= Constants.GRID_MAX_SIDE) {
                    break;
                } else {
                    if (gridSize < Constants.GRID_MIN_SIZE) {
                        System.out.println(Messages.MIN_GRID_MESSAGE + Constants.GRID_MIN_SIZE + ".");
                    } else if (gridSize > Constants.GRID_MAX_SIDE) {
                        System.out.println(Messages.MAX_GRID_MESSAGE + Constants.GRID_MAX_SIDE + ".");
                    }
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
        return gridSize;
    }

    public int getNumberOfMines(int gridSize) {
        int mineCount;
        int maxMines = (int) (gridSize * gridSize * Constants.MAX_MINE);
        while (true) {
            System.out.print(Messages.MINE_MESSAGE);
            if (scanner.hasNextInt()) {
                mineCount = scanner.nextInt();
                if (mineCount >= 1 && mineCount <= maxMines) {
                    break;
                } else {
                    System.out.println("Invalid number of mines. Please enter a number between 1 and " + maxMines + ".");
                }
            } else {
                System.out.println("Incorrect input. Please enter a number.");
                scanner.nextLine();
            }
        }
        return mineCount;
    }

    public void displayGame(Game game) {
        System.out.println(Messages.MINEFIELD_MESSAGE);
        System.out.println(game.toString());
        System.out.println();
    }

    public String getInputString(int gridSize) {
        String input;
        while (true) {
            System.out.print(Messages.SELECT_SQUARE_MESSAGE);
            input = scanner.next().toUpperCase();
            if (isValidInput(input, gridSize)) {
                break;
            } else {
                System.out.println("Incorrect input. Please enter the correct format (e.g. A1).");
            }
        }
        scanner.nextLine(); // clear input buffer
        return input;
    }

    public void displayGameState(Game game) {
        if (game.isGameWon()) {
            game.setGameState(GameState.WON);
            System.out.println(Messages.WON_MESSAGE);
        } else if (game.getGameState() == GameState.LOST) {
            System.out.println(Messages.DETONATED_MINE_MESSAGE);
        }
    }

    public void playAgainPrompt() {
        System.out.println(Messages.PLAY_AGAIN_MESSAGE);
        scanner.nextLine();
    }

    private boolean isValidInput(String input, int gridSize) {
        if (input.length() >= 2) {
            char row = input.charAt(0);
            String colStr = input.substring(1);
            if (Character.isLetter(row) && Utils.isNumeric(colStr)) {
                int rowNum = row - 'A';
                int colNum = Integer.parseInt(colStr) - 1;
                if (rowNum >= 0 && rowNum < gridSize && colNum >= 0 && colNum < gridSize) {
                    return true;
                }
            }
        }
        return false;
    }

}
