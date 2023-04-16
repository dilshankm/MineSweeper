package com.minesweeper;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Welcome to Minesweeper!");
            System.out.println();

            int gridSize = getGridSize(scanner);
            int mineCount = getNumberOfMines(scanner, gridSize);

            Game game = new Game(gridSize, mineCount);

            while (game.getGameState() == GameState.PLAYING) {
                System.out.println();
                System.out.println(game.toString());
                System.out.println();

                String input = getInputString(scanner, "Select a square to reveal (e.g. A1): ");
                game.selectSquare(input);

                if (game.isGameWon()) {
                    game.setGameState(GameState.WON);
                    System.out.println("Congratulations, you have won the game!");
                } else if (game.getGameState() == GameState.LOST) {
                    System.out.println("Oh no, you detonated a mine! Game over.");
                }
            }
            System.out.println("Press any key to play again...");
            scanner.nextLine();
        }
    }

    private static int getGridSize(Scanner scanner) {
        int gridSize;
        while (true) {
            System.out.print("Enter the size of the grid (e.g. 4 for a 4x4 grid): ");
            if (scanner.hasNextInt()) {
                gridSize = scanner.nextInt();
                if (gridSize >= 2) {
                    break;
                } else {
                    System.out.println("Invalid grid size. Please enter a number greater than or equal to 2.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
        return gridSize;
    }

    private static int getNumberOfMines(Scanner scanner, int gridSize) {
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
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
        return mineCount;
    }

    private static String getInputString(Scanner scanner, String prompt) {
        System.out.print(prompt);
        String input = scanner.next().toUpperCase();
        scanner.nextLine(); // clear input buffer
        return input;
    }
}
