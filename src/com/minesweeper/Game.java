package com.minesweeper;

public class Game implements IGame {
    private final GameGrid grid;
    private final int totalMines;
    private int revealedCount;
    private GameState gameState;

    public Game(int size, int mineCount) {
        this.grid = new GameGrid(size, mineCount);
        this.totalMines = mineCount;
        this.revealedCount = 0;
        this.gameState = GameState.PLAYING;
    }

    public void selectSquare(String input) {
        Position pos = grid.parseCoordinate(input);
        Cell cell = grid.getCell(pos.row(), pos.col());
        if (cell.isMine()) {
            cell.setRevealed(true);
            gameState = GameState.LOST;
        } else {
            revealCell(pos.row(), pos.col());
            if (revealedCount == grid.getSize() * grid.getSize() - totalMines) {
                gameState = GameState.WON;
            }
        }
    }

    private void revealCell(int row, int col) {
        if (!grid.isInsideGrid(row, col)) {
            return;
        }
        Cell cell = grid.getCell(row, col);
        if (cell.isRevealed()) {
            return;
        }
        cell.setRevealed(true);
        revealedCount++;
        if (cell.getAdjacentMines() == 0) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    int newRow = row + i;
                    int newCol = col + j;
                    if (!(i == 0 && j == 0) && grid.isInsideGrid(newRow, newCol)) {
                        revealCell(newRow, newCol);
                    }
                }
            }
        }
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

    @Override
    public String toString() {
        return grid.toString();
    }
}
