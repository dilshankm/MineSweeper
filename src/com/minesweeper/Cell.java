package com.minesweeper;

public class Cell {
    private boolean isMine;
    private boolean isRevealed;
    private int adjacentMines;

    public Cell(boolean isMine, int adjacentMines) {
        this.isMine = isMine;
        this.isRevealed = false;
        this.adjacentMines = adjacentMines;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }

    public int getAdjacentMines() {
        return adjacentMines;
    }

    public void setAdjacentMines(int adjacentMines) {
        this.adjacentMines = adjacentMines;
    }


    @Override
    public String toString() {
        if (!isRevealed) {
            return "_";
        } else if (isMine) {
            return "*";
        } else {
            return String.valueOf(adjacentMines);
        }
    }


}
