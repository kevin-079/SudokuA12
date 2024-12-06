/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #1
 * 1 - 5026231008 - Batara Haryo Yudanto
 * 2 - 5026231079 - Kevin Nathanael
 * 3 - 5026231089 - Yusuf Acala Sadurjaya Sri Krisna
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameBoardPanel extends JPanel {
    private static final long serialVersionUID = 1L;  // To prevent serial warning

    // Define named constants for UI sizes
    public static final int CELL_SIZE = 60;   // Cell width/height in pixels
    public static final int BOARD_WIDTH  = CELL_SIZE * SudokuConstants.GRID_SIZE;
    public static final int BOARD_HEIGHT = CELL_SIZE * SudokuConstants.GRID_SIZE;  // Board width/height in pixels

    // Define properties
    private Cell[][] cells = new Cell[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];  // The 9x9 cells
    private Puzzle puzzle = new Puzzle();  // Puzzle data
    private JTextField statusBar;  // Status bar to show messages
    private Timer timer;  // Timer to track game time
    private int secondsElapsed = 0;  // Time tracker
    private JProgressBar progressBar;  // Progress bar to show game progress

    // Constructor
    public GameBoardPanel() {
        super.setLayout(new GridLayout(SudokuConstants.GRID_SIZE, SudokuConstants.GRID_SIZE));  // JPanel

        // Allocate the 2D array of Cell, and add into JPanel
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col] = new Cell(row, col);
                super.add(cells[row][col]);   // JPanel
            }
        }

        // Add action listeners for editable cells
        CellInputListener listener = new CellInputListener();
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if (cells[row][col].isEditable()) {
                    cells[row][col].addActionListener(listener);
                }
            }
        }
        
        super.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));

        // Initialize status bar and progress bar
        statusBar = new JTextField("Welcome to Sudoku!");
        statusBar.setEditable(false);
        statusBar.setBackground(Color.LIGHT_GRAY);
        add(statusBar, BorderLayout.SOUTH);

        progressBar = new JProgressBar(0, 81);  // 81 cells in Sudoku
        progressBar.setStringPainted(true);
        add(progressBar, BorderLayout.SOUTH);

        // Initialize Timer
        timer = new Timer(1000, e -> {
            secondsElapsed++;
            updateStatusBar();
        });

        timer.start();  // Start the timer immediately
    }

    // Generate a new puzzle; reset the game board of cells based on the puzzle
    public void newGame() {
        puzzle.newPuzzle(2);  // Generate new puzzle with difficulty level 2

        // Initialize all the 9x9 cells based on the puzzle
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col]);
            }
        }

        // Reset timer and progress bar
        secondsElapsed = 0;
        progressBar.setValue(0);
        statusBar.setText("New game started!");
    }

    // Update status bar with the current status (time, score, and remaining cells)
    private void updateStatusBar() {
        int remainingCells = getRemainingCells();
        int filledCells = 81 - remainingCells;
        progressBar.setValue(filledCells);

        statusBar.setText("Time: " + secondsElapsed + " seconds | Remaining Cells: " + remainingCells);
    }

    // Get the number of remaining cells (empty cells)
    private int getRemainingCells() {
        int remaining = 0;
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if (cells[row][col].getText().equals("")) {
                    remaining++;
                }
            }
        }
        return remaining;
    }

    // Check if the puzzle is solved
    public boolean isSolved() {
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if (cells[row][col].status == CellStatus.TO_GUESS || cells[row][col].status == CellStatus.WRONG_GUESS) {
                    return false;
                }
            }
        }
        return true;
    }

    // Listener for editable cells
    private class CellInputListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Get the cell that triggered the event
            Cell sourceCell = (Cell) e.getSource();
            int numberIn = Integer.parseInt(sourceCell.getText());

            // Check if the entered number is correct
            if (numberIn == sourceCell.number) {
                sourceCell.status = CellStatus.CORRECT_GUESS;
                String effectFile = "Correct.wav";
                SoundEffect effect = new SoundEffect();
                effect.playSound(effectFile);
            } else {
                sourceCell.status = CellStatus.WRONG_GUESS;
                String effectFile = "False.wav";
                SoundEffect effect = new SoundEffect();
                effect.playSound(effectFile);
            }

            // Repaint the cell based on its status
            sourceCell.paint();

            // Check if the game is solved
            if (isSolved()) {
                JOptionPane.showMessageDialog(null, "Congratulations! You solved the puzzle.");
                timer.stop();  // Stop the timer when the puzzle is solved
            }
        }
    }

    // Reset the game and start a new game
    public void resetGame() {
        newGame();  // Start a new game
        timer.restart();  // Restart the timer
    }
}
