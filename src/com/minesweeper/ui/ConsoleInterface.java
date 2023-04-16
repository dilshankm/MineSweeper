// ConsoleInterface.java
package com.minesweeper.ui;

import com.minesweeper.Game;
import com.minesweeper.GameState;

import java.util.Scanner;

public class ConsoleInterface implements UserInterface {
    private Scanner scanner;

    public ConsoleInterface() {
        scanner = new Scanner(System.in);
    }

    public void displayWelcomeMessage() {
        System.out.println("Welcome to Minesweeper!");
        System.out.println();
    }

    public int getGridSize() {
        int gridSize;
        int minSize = 3;
        int maxSize = 10;
        while (true) {
            System.out.print("Enter the size of the grid (e.g. 4 for a 4x4 grid): ");
            if (scanner.hasNextInt()) {
                gridSize = scanner.nextInt();
                if (gridSize >= minSize && gridSize <= maxSize) {
                    break;
                } else {
                    if (gridSize < minSize) {
                        System.out.println("Minimum size of grid is " + minSize + ".");
                    } else if (gridSize > maxSize) {
                        System.out.println("Maximum size of grid is " + maxSize + ".");
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
        int maxMines = (int) (gridSize * gridSize * 0.35);
        while (true) {
            System.out.print("Enter the number of mines to place on the grid (maximum is 35% of the total squares): ");
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
        System.out.println();
        System.out.println(game.toString());
        System.out.println();
    }

    public String getInputString(int gridSize) {
        String input;
        while (true) {
            System.out.print("Select a square to reveal (e.g. A1): ");
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
            System.out.println("Congratulations, you have won the game!");
        } else if (game.getGameState() == GameState.LOST) {
            System.out.println("Oh no, you detonated a mine! Game over.");
        }
    }

    public void playAgainPrompt() {
        System.out.println("Press any key to play again...");
        scanner.nextLine();
    }

    private static boolean isNumeric(String str) {
        return str.chars().allMatch(Character::isDigit);
    }
    private boolean isValidInput(String input, int gridSize) {
        if (input.length() >= 2) {
            char row = input.charAt(0);
            String colStr = input.substring(1);
            if (Character.isLetter(row) && isNumeric(colStr)) {
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
