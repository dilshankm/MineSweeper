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
        this(new DefaultScannerFactory());
    }

    public ConsoleInterface(ScannerFactory scannerFactory) {
        this.scanner = scannerFactory.createScanner();
    }



    protected void print(String message) {
        System.out.print(message);
    }

    protected void println(String message) {
        System.out.println(message);
    }

    public void displayWelcomeMessage() {
        println(Messages.WELCOME_MESSAGE);
        println("");
    }

    public int getGridSize() {
        int gridSize;
        while (true) {
            print(Messages.GRID_MESSAGE);
            if (scanner.hasNextInt()) {
                gridSize = scanner.nextInt();
                if (isValidGridSize(gridSize)) {
                    break;
                }
            } else {
                println(Messages.INCORRECT_MESSAGE);
                scanner.nextLine();
            }
        }
        return gridSize;
    }

    private boolean isValidGridSize(int gridSize) {
        if (gridSize < Constants.GRID_MIN_SIZE) {
            println(Messages.MIN_GRID_MESSAGE + Constants.GRID_MIN_SIZE + ".");
            return false;
        } else if (gridSize > Constants.GRID_MAX_SIDE) {
            println(Messages.MAX_GRID_MESSAGE + Constants.GRID_MAX_SIDE + ".");
            return false;
        }
        return true;
    }

    public int getNumberOfMines(int gridSize) {
        int mineCount;
        int maxMines = (int) (gridSize * gridSize * Constants.MAX_MINE);
        while (true) {
            print(Messages.MINE_MESSAGE);
            if (scanner.hasNextInt()) {
                mineCount = scanner.nextInt();
                if (isValidMineCount(mineCount, maxMines)) {
                    break;
                }
            } else {
                println(Messages.INCORRECT_MESSAGE);
                scanner.next();
            }
        }
        return mineCount;
    }

    private boolean isValidMineCount(int mineCount, int maxMines) {
        if (mineCount < 1) {
            println(Messages.AT_LEAST_ONE_MINE_MESSAGE);
            return false;
        } else if (mineCount > maxMines) {
            println(Messages.MAX_MINE_COUNT_MESSAGE);
            return false;
        }
        return true;
    }

    public void displayGame(Game game) {
        println("");
        println(game.getRevealedCount() >= 1 ? Messages.UPDATED_MINEFIELD_MESSAGE : Messages.MINEFIELD_MESSAGE);
        println(game.toString());
    }

    public void displaySquareInfo(Game game, int row, int col) {
        int mines = game.getAdjacentMinesAt(row, col);
        System.out.printf(Messages.SQUARE_INFO_FORMAT + "%n", mines);
    }

    public String getInputString(int gridSize) {
        String input;
        while (true) {
            print(Messages.SELECT_SQUARE_MESSAGE);
            input = scanner.next().toUpperCase();
            if (isValidInput(input, gridSize)) {
                break;
            } else {
                println(Messages.INCORRECT_MESSAGE);
            }
        }
        scanner.nextLine();
        return input;
    }

    public void displayGameState(Game game) {
        if (game.isGameWon()) {
            game.setGameState(GameState.WON);
            println(Messages.WON_MESSAGE);
        } else if (game.getGameState() == GameState.LOST) {
            println(Messages.DETONATED_MINE_MESSAGE);
        }
    }

    public void playAgainPrompt() {
        println(Messages.PLAY_AGAIN_MESSAGE);
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

    public void setScanner(Scanner testScanner) {
        this.scanner = testScanner;
    }
}




