/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #1
 * 1 - 5026231008 - Batara Haryo Yudanto
 * 2 - 5026231079 - Kevin Nathanael
 * 3 - 5026231089 - Yusuf Acala Sadurjaya Sri Krisna
 */

// Puzzle.java

import java.util.Random;

public class Puzzle {
    int[][] numbers = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    boolean[][] isGiven = new boolean[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];

    public void newPuzzle(int cellsToGuess) {
        generateSolution();
        clearCells(cellsToGuess);
    }

    private void generateSolution() {
        int[][] predefined = {
                {5, 3, 4, 6, 7, 8, 9, 1, 2},
                {6, 7, 2, 1, 9, 5, 3, 4, 8},
                {1, 9, 8, 3, 4, 2, 5, 6, 7},
                {8, 5, 9, 7, 6, 1, 4, 2, 3},
                {4, 2, 6, 8, 5, 3, 7, 9, 1},
                {7, 1, 3, 9, 2, 4, 8, 5, 6},
                {9, 6, 1, 5, 3, 7, 2, 8, 4},
                {2, 8, 7, 4, 1, 9, 6, 3, 5},
                {3, 4, 5, 2, 8, 6, 1, 7, 9},
        };

        for (int i = 0; i < SudokuConstants.GRID_SIZE; i++) {
            for (int j = 0; j < SudokuConstants.GRID_SIZE; j++) {
                numbers[i][j] = predefined[i][j];
                isGiven[i][j] = true;
            }
        }
    }

    private void clearCells(int cellsToGuess) {
        Random random = new Random();
        int cellsCleared = 0;

        while (cellsCleared < cellsToGuess) {
            int row = random.nextInt(SudokuConstants.GRID_SIZE);
            int col = random.nextInt(SudokuConstants.GRID_SIZE);

            if (isGiven[row][col]) {
                isGiven[row][col] = false;
                cellsCleared++;
            }
        }
    }
}

