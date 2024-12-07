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
    private int currentScore;
    private JProgressBar progressBar;
    private String playerName = "Player";  // Default name
    private int highScore = Integer.MAX_VALUE;  // Store the highest score

    private JPanel welcomePanel;  // Panel for Welcome Screen

    public Main() {
        setTitle("Sudoku");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel for Welcome Screen
        welcomePanel = new JPanel();
        welcomePanel.setLayout(new BorderLayout());
        welcomePanel.setBackground(Color.CYAN);

        // Add Welcome Message
        JLabel welcomeLabel = new JLabel("Welcome to Sudoku!", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE);
        welcomePanel.add(welcomeLabel, BorderLayout.CENTER);

        // Add Start Game Button
        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.PLAIN, 18));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showGameScreen();
            }
        });
        welcomePanel.add(startButton, BorderLayout.SOUTH);

        // Add Exit Button
        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.PLAIN, 18));
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);  // Exit the game
            }
        });
        welcomePanel.add(exitButton, BorderLayout.NORTH);

        // Display Welcome Screen
        add(welcomePanel);

        setVisible(true);
    }

    private void showGameScreen() {
        // Remove the welcome screen
        remove(welcomePanel);

        // Initialize the game board
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
        progressBar = new JProgressBar(0, 81); 
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

        // Make the game screen visible
        setVisible(true);

        // Play Background Music
        String BGMFile = "BGM.wav";
        BGM background = new BGM();
        background.BGMusic(BGMFile);
    }

    private void startNewGame() {
        // Prompt for player name
        playerName = JOptionPane.showInputDialog(this, "Enter your name:", "New Game", JOptionPane.QUESTION_MESSAGE);
        if (playerName == null || playerName.trim().isEmpty()) {
            playerName = "Player";  // Default name if player doesn't enter anything
        }

        gameBoardPanel.newGame();  // Reset board to a new game
        secondsElapsed = 0;  // Reset timer
        currentScore = 0;  // Reset current score
        timer.start();
        statusBar.setText("Welcome " + playerName + "!");
        updateStatusBar();
    }

    private void updateStatusBar() {
        int remainingCells = gameBoardPanel.getRemainingCells(); // Get the remaining empty cells
        progressBar.setValue(81 - remainingCells); // Update progress bar
        statusBar.setText("Time: " + secondsElapsed + " seconds | Remaining Cells: " + remainingCells +
                " | High Score: " + highScore + " seconds | Player: " + playerName);
    }

    private void checkAndUpdateHighScore() {
        if (gameBoardPanel.isSolved() && secondsElapsed < highScore) {
            highScore = secondsElapsed;  // Update high score
            JOptionPane.showMessageDialog(this, "Congratulations " + playerName + "! New high score: " + highScore + " seconds");
        }
    }

    private void checkGameCompletion() {
        if (gameBoardPanel.isSolved()) {
            checkAndUpdateHighScore();
            JOptionPane.showMessageDialog(null, "Congratulations, you solved the puzzle!");
        }
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

    private void resetGame() {
        gameBoardPanel.reset();  // Reset the board
        secondsElapsed = 0;  // Reset timer
        currentScore = 0;  // Reset current score
        updateStatusBar();
    }

    private void toggleTimer() {
        if (timer.isRunning()) {
            timer.stop();
        } else {
            timer.start();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
    }
}
