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

public class Main extends JFrame {
    private GameBoardPanel gameBoardPanel;
    private JTextField statusBar;
    private Timer timer;
    private int secondsElapsed;
    private JProgressBar progressBar;

    public Main() {
        setTitle("Sudoku");
        setSize(600, 700); // Menambahkan ruang untuk status bar dan progress bar
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Game board panel
        gameBoardPanel = new GameBoardPanel();
        add(gameBoardPanel, BorderLayout.CENTER);

        // Control panel with buttons (New Game, Reset)
        JPanel controlPanel = new JPanel();
        JButton newGameButton = new JButton("New Game");
        JButton resetButton = new JButton("Reset");

        controlPanel.add(newGameButton);
        controlPanel.add(resetButton);
        add(controlPanel, BorderLayout.NORTH);

        // Status bar
        statusBar = new JTextField("Welcome to Sudoku!");
        statusBar.setEditable(false);
        statusBar.setBackground(Color.LIGHT_GRAY);
        add(statusBar, BorderLayout.SOUTH);

        // Progress Bar (for remaining cells)
        progressBar = new JProgressBar(0, 81); // 81 cells in a Sudoku board
        progressBar.setStringPainted(true);
        add(progressBar, BorderLayout.SOUTH);

        // Action listener for New Game button
        newGameButton.addActionListener(e -> startNewGame());

        // Action listener for Reset button
        resetButton.addActionListener(e -> resetGame());

        // Create Menu Bar
        setJMenuBar(createMenuBar());

        // Initialize timer
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                secondsElapsed++;
                updateStatusBar();
            }
        });

        setVisible(true);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // File Menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem newGameMenuItem = new JMenuItem("New Game");
        newGameMenuItem.addActionListener(e -> startNewGame());
        JMenuItem resetMenuItem = new JMenuItem("Reset");
        resetMenuItem.addActionListener(e -> resetGame());
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(e -> System.exit(0));
        fileMenu.add(newGameMenuItem);
        fileMenu.add(resetMenuItem);
        fileMenu.add(exitMenuItem);

        // Options Menu
        JMenu optionsMenu = new JMenu("Options");
        JMenuItem pauseResumeItem = new JMenuItem("Pause/Resume Timer");
        pauseResumeItem.addActionListener(e -> toggleTimer());
        optionsMenu.add(pauseResumeItem);

        // Help Menu
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> JOptionPane.showMessageDialog(this, "Sudoku Game\nVersion 1.0"));
        helpMenu.add(aboutItem);

        // Add menus to menu bar
        menuBar.add(fileMenu);
        menuBar.add(optionsMenu);
        menuBar.add(helpMenu);

        return menuBar;
    }

    private void startNewGame() {
        gameBoardPanel.newGame(); // Reset board to a new game
        secondsElapsed = 0; // Reset timer
        timer.start();
        statusBar.setText("New game started!");
        updateStatusBar();
    }

    private void resetGame() {
        gameBoardPanel.resetGame(); // Reset game board
        secondsElapsed = 0; // Reset timer
        statusBar.setText("Game reset!");
        updateStatusBar();
    }

    private void updateStatusBar() {
        int remainingCells = gameBoardPanel.getRemainingCells(); // Get the remaining empty cells
        progressBar.setValue(81 - remainingCells); // Update progress bar
        statusBar.setText("Time: " + secondsElapsed + " seconds | Remaining Cells: " + remainingCells);
    }

    private void toggleTimer() {
        if (timer.isRunning()) {
            timer.stop();
            statusBar.setText("Game paused.");
        } else {
            timer.start();
            statusBar.setText("Game resumed.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
    }
}
