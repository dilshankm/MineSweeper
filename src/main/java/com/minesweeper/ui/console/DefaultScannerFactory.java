package com.minesweeper.ui.console;

import java.util.Scanner;

class DefaultScannerFactory implements ScannerFactory {
    public Scanner createScanner() {
        return new Scanner(System.in);
    }
}