package com.minesweeper.game;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Game implements IGame {
    private final GameGrid grid;
    private final int totalMines;
    private int revealedCount;
    private GameState gameState;

    public Game(int size, int mineCount, GameGrid customGrid) {
        this.grid = customGrid;
        this.totalMines = mineCount;
        this.revealedCount = 0;
        this.gameState = GameState.PLAYING;
    }

    public Game(int size, int mineCount) {
        this(size, mineCount, new GameGrid(size, mineCount));
    }

    public int getRevealedCount() {
        return revealedCount;
    }

    public int getAdjacentMinesAt(int row, int col) {
        return grid.getCell(row, col).getAdjacentMines();
    }

    public boolean isGameWon() {
        return gameState == GameState.WON;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void selectSquare(String input) {
        var pos = grid.parseCoordinate(input);
        var cell = grid.getCell(pos.row(), pos.col());
        if (cell.isMine()) {
            cell.setRevealed(true);
            gameState = GameState.LOST;
        } else {
            revealedCount += revealCell(pos.row(), pos.col());
            if (revealedCount == grid.getSize() * grid.getSize() - totalMines) {
                gameState = GameState.WON;
            }
        }
    }

    private List<Position> getNeighbors(int row, int col) {
        return IntStream.rangeClosed(-1, 1)
                .boxed()
                .flatMap(i -> IntStream.rangeClosed(-1, 1)
                        .filter(j -> i != 0 || j != 0)
                        .mapToObj(j -> new Position(row + i, col + j)))
                .filter(pos -> grid.isInsideGrid(pos.row(), pos.col()))
                .collect(Collectors.toList());
    }

    private int revealCell(int row, int col) {
        if (!grid.isInsideGrid(row, col)) {
            return 0;
        }
        var cell = grid.getCell(row, col);
        if (cell.isRevealed()) {
            return 0;
        }
        cell.setRevealed(true);

        if (cell.getAdjacentMines() == 0) {
            var neighbors = getNeighbors(row, col);
            return 1 + neighbors.stream().mapToInt(pos -> revealCell(pos.row(), pos.col())).sum();
        } else {
            return 1;
        }
    }

    @Override
    public String toString() {
        return grid.toString();
    }
}
