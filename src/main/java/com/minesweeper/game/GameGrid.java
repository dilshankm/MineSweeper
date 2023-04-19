package com.minesweeper.game;

import com.minesweeper.exceptions.InvalidInputException;

import java.util.Random;
import java.util.stream.IntStream;

public class GameGrid implements IGrid {
    private final Cell[][] grid;
    private final int size;
    private final int mineCount;

    public Cell getCell(int row, int col) {
        return grid[row][col];
    }

    public int getSize() {
        return size;
    }

    public GameGrid(int size, int mineCount) {
        this.size = size;
        this.mineCount = mineCount;
        this.grid = new Cell[size][size];
        initializeGrid();
        plantMines();
        setAdjacentMines();
    }

    private void initializeGrid() {
        IntStream.range(0, size).forEach(i ->
                IntStream.range(0, size).forEach(j ->
                        grid[i][j] = new Cell(false, 0)));
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
        IntStream.range(0, size).forEach(i ->
                IntStream.range(0, size).forEach(j -> {
                    int adjacentMines = countAdjacentMines(i, j);
                    Cell cell = grid[i][j];
                    cell.setAdjacentMines(adjacentMines);
                }));
    }

    private int countAdjacentMines(int row, int col) {
        return (int) IntStream.rangeClosed(-1, 1).flatMap(i ->
                IntStream.rangeClosed(-1, 1).map(j -> {
                    int newRow = row + i;
                    int newCol = col + j;
                    if (isInsideGrid(newRow, newCol)) {
                        return grid[newRow][newCol].isMine() ? 1 : 0;
                    }
                    return 0;
                })).sum();
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
        IntStream.rangeClosed(1, size).forEach(i -> sb.append(i).append(" "));
        sb.append("\n");
        IntStream.range(0, size).forEach(i -> {
            sb.append((char) ('A' + i)).append(" ");
            IntStream.range(0, size).forEach(j -> sb.append(grid[i][j].toString()).append(" "));
            sb.append("\n");
        });
        return sb.toString();
    }
}
