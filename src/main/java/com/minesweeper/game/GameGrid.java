package com.minesweeper.game;

import com.minesweeper.exceptions.InvalidInputException;

import java.util.Random;

public class GameGrid implements IGrid {
    private final Cell[][] grid;
    private final int size;
    private final int mineCount;

    public GameGrid(int size, int mineCount) {
        this.size = size;
        this.mineCount = mineCount;
        this.grid = new Cell[size][size];
        initializeGrid();
        plantMines();
        setAdjacentMines();
    }

    private void initializeGrid() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = new Cell(false, 0);
            }
        }
    }

    private void plantMines() {
        Random random = new Random();
        int count = 0;
        while (count < mineCount) {
            int row = random.nextInt(size);
            int col = random.nextInt(size);
            Cell cell = grid[row][col];
            if (!cell.isMine()) {
                cell.setMine(true);
                count++;
            }
        }
    }

    private void setAdjacentMines() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int adjacentMines = countAdjacentMines(i, j);
                Cell cell = grid[i][j];
                cell.setAdjacentMines(adjacentMines);
            }
        }
    }

    private int countAdjacentMines(int row, int col) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newRow = row + i;
                int newCol = col + j;
                if (isInsideGrid(newRow, newCol)) {
                    Cell cell = grid[newRow][newCol];
                    if (cell.isMine()) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public Cell getCell(int row, int col) {
        return grid[row][col];
    }

    public int getSize() {
        return size;
    }

    public boolean isInsideGrid(int row, int col) {
        return row >= 0 && row < size && col >= 0 && col < size;
    }

    public Position parseCoordinate(String input) {
        try {
            int row = input.charAt(0) - 'A';
            int col = Integer.parseInt(input.substring(1)) - 1;
            return new Position(row, col);
        } catch (Exception e) {
            throw new InvalidInputException();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("  ");
        for (int i = 1; i <= size; i++) {
            sb.append(i).append(" ");
        }
        sb.append("\n");
        for (int i = 0; i < size; i++) {
            sb.append((char) ('A' + i)).append(" ");
            for (int j = 0; j < size; j++) {
                sb.append(grid[i][j].toString()).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
