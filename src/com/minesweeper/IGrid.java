package com.minesweeper;

public interface IGrid {

    Cell getCell(int row, int col);

    int getSize();

    boolean isInsideGrid(int row, int col);

    Position parseCoordinate(String input);

    @Override
    String toString();

}