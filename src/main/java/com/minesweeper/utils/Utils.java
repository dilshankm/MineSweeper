package com.minesweeper.utils;

public class Utils {

    public static boolean isNumeric(String str) {
        return str.chars().allMatch(Character::isDigit);
    }
}
