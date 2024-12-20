/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #1
 * 1 - 5026231008 - Batara Haryo Yudanto
 * 2 - 5026231079 - Kevin Nathanael
 * 3 - 5026231089 - Yusuf Acala Sadurjaya Sri Krisna
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class GameBoardPanel extends JPanel {
    private Cell[][] cells = new Cell[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    private Puzzle puzzle = new Puzzle();
    private Timer timer;
    private int remainingCells;
    private Clip backgroundMusic;

    public GameBoardPanel() {
        setLayout(new GridLayout(SudokuConstants.GRID_SIZE, SudokuConstants.GRID_SIZE));

        Font customFont = new Font("Comic Sans MS", Font.BOLD, 24); // Ubah angkanya agar enak dilihat

        playBackgroundMusic("play.wav");

        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col] = new Cell(row, col);
                cells[row][col].setFont(customFont); // Gunakan di setiap pemakaian
                cells[row][col].addActionListener(new CellInputListener(row, col));
                cells[row][col].setBorder(BorderFactory.createMatteBorder(
                        (row % 3 == 0) ? 4 : 1,
                        (col % 3 == 0) ? 4 : 1,
                        (row == SudokuConstants.GRID_SIZE - 1) ? 4 : 1,
                        (col == SudokuConstants.GRID_SIZE - 1) ? 4 : 1,
                        Color.BLACK
                ));
                cells[row][col].setBackground(new Color(30, 30, 60)); // Atur backgorund cellnya jadi dark blue
                cells[row][col].setForeground(Color.WHITE); // Atur angkanya berwarna cerah
                add(cells[row][col]);
            }
        }
    }

    public void newGame(int cellsToGuess) {
        puzzle.newPuzzle(cellsToGuess);
        remainingCells = cellsToGuess;
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col]);
                if (puzzle.isGiven[row][col]) {
                    cells[row][col].setBackground(new Color(50, 50, 90)); // Beri warna berbeda pada cell yang harus diisi
                    cells[row][col].setForeground(Color.WHITE);
                } else {
                    cells[row][col].setBackground(new Color(30, 30, 60)); // Warna berbeda pada cell yang telah terisi
                    cells[row][col].setForeground(Color.WHITE);
                }
            }
        }
    }

    public void revealHint() {
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if (!puzzle.isGiven[row][col] && cells[row][col].getText().isEmpty()) {
                    final int finalRow = row;
                    final int finalCol = col;
                    cells[finalRow][finalCol].setText(String.valueOf(puzzle.numbers[finalRow][finalCol]));
                    cells[finalRow][finalCol].setEditable(false);
                    cells[finalRow][finalCol].setBackground(Color.GREEN);
                    Timer timer = new Timer(200, e -> cells[finalRow][finalCol].setBackground(new Color(30, 30, 60)));
                    timer.setRepeats(false);
                    timer.start();
                    return;
                }
            }
        }
    }

    private void checkWinCondition() {
        boolean allCellsCorrect = true;
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                String cellText = cells[row][col].getText();
                if (cellText.isEmpty() || !cellText.equals(String.valueOf(puzzle.numbers[row][col]))) {
                    allCellsCorrect = false;
                    break;
                }
            }
            if (!allCellsCorrect) break;
        }

        if (allCellsCorrect) {
            stopBackgroundMusic();
            playSound("win.wav");
            JOptionPane.showMessageDialog(this, "Congratulations! You win!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            int option = JOptionPane.showOptionDialog(this, "Main lagi atau Exit?", "Selamat Anda Menang!",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Main Lagi", "Keluar"}, "Main Lagi");
            if (option == JOptionPane.YES_OPTION) {
                ((JFrame) SwingUtilities.getWindowAncestor(this)).dispose();
                new Main(); // Jika selesai maka kembali ke awal
            } else {
                System.exit(0);
            }
        }
    }

    public void handleTimesUp() {
        stopBackgroundMusic();
        JOptionPane.showMessageDialog(this, "Time's up! You lose.", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        int option = JOptionPane.showOptionDialog(this, "Coba lagi atau Keluar?", "Waktu Habis!",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Coba Lagi", "Keluar"}, "Coba Lagi");
        if (option == JOptionPane.YES_OPTION) {
            ((JFrame) SwingUtilities.getWindowAncestor(this)).dispose();
            new Main(); // Kembali ke pilih level
        } else {
            System.exit(0);
        }
    }

    private void playSound(String soundFile) {
        try {
            File file = new File(soundFile);
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void playBackgroundMusic(String soundFile) {
        try {
            File file = new File(soundFile);
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(file);
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(audioInput);
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
            backgroundMusic.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopBackgroundMusic() {
        if (backgroundMusic != null && backgroundMusic.isRunning()) {
            backgroundMusic.stop();
            backgroundMusic.close();
        }
    }

    private class CellInputListener implements ActionListener {
        private final int row;
        private final int col;

        public CellInputListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField source = (JTextField) e.getSource();
            try {
                int enteredValue = Integer.parseInt(source.getText());
                if (enteredValue == puzzle.numbers[row][col]) {
                    playSound("correct.wav");
                    cells[row][col].setBackground(Color.GREEN);
                    remainingCells--;
                } else {
                    playSound("false.wav");
                    cells[row][col].setBackground(Color.RED);
                }
                Timer timer = new Timer(200, ev -> cells[row][col].setBackground(new Color(30, 30, 60)));
                timer.setRepeats(false);
                timer.start();
                checkWinCondition();
            } catch (NumberFormatException ex) {
                playSound("false.wav");
                cells[row][col].setBackground(Color.RED);
                Timer timer = new Timer(200, ev -> cells[row][col].setBackground(new Color(30, 30, 60)));
                timer.setRepeats(false);
                timer.start();
            }
        }
    }
}
