/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #1
 * 1 - 5026231008 - Batara Haryo Yudanto
 * 2 - 5026231079 - Kevin Nathanael
 * 3 - 5026231089 - Yusuf Acala Sadurjaya Sri Krisna
 */

// Main.java

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Main extends JFrame {
    private GameBoardPanel gameBoardPanel;
    private JTextField statusBar;
    private Timer timer;
    private int secondsRemaining;
    private String currentLevel;

    public Main() {
        setTitle("Sudoku A12");
        setSize(700, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        showWelcomeScreen();
        setVisible(true);
    }

    private void showWelcomeScreen() {
        getContentPane().removeAll();
        revalidate();
        repaint();

        JPanel welcomePanel = new JPanel(new BorderLayout());
        welcomePanel.setBackground(Color.LIGHT_GRAY);

        JLabel welcomeLabel = new JLabel("Welcome to Sudoku A12!", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Verdana", Font.BOLD, 34));
        welcomePanel.add(welcomeLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        String[] modes = {"Easy", "Medium", "Hard"};
        for (String mode : modes) {
            JButton modeButton = new JButton(mode);
            modeButton.addActionListener(e -> startGame(mode));
            buttonPanel.add(modeButton);
        }
        welcomePanel.add(buttonPanel, BorderLayout.SOUTH);

        add(welcomePanel);
        validate();
    }

    private void startGame(String difficulty) {
        getContentPane().removeAll();
        revalidate();
        repaint();

        currentLevel = difficulty;
        gameBoardPanel = new GameBoardPanel();
        gameBoardPanel.newGame(getDifficultyLevel(difficulty));
        add(gameBoardPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener(e -> restartGame());
        controlPanel.add(restartButton);

        JButton newGameButton = new JButton("New Game");
        newGameButton.addActionListener(e -> showWelcomeScreen());
        controlPanel.add(newGameButton);

        JButton hintButton = new JButton("Hint");
        hintButton.addActionListener(e -> gameBoardPanel.revealHint());
        controlPanel.add(hintButton);

        add(controlPanel, BorderLayout.NORTH);

        statusBar = new JTextField("Game started in " + difficulty + " mode!");
        statusBar.setEditable(false);
        statusBar.setBackground(Color.LIGHT_GRAY);
        add(statusBar, BorderLayout.SOUTH);

        setTimer(difficulty);
        validate();
    }

    private void restartGame() {
        startGame(currentLevel);
    }

    private void setTimer(String difficulty) {
        switch (difficulty) {
            case "Easy":
                secondsRemaining = 300;
                break;
            case "Medium":
                secondsRemaining = 150;
                break;
            case "Hard":
                secondsRemaining = 100;
                break;
        }

        if (timer != null) {
            timer.stop();
        }

        timer = new Timer(1000, e -> {
            secondsRemaining--;
            statusBar.setText("Time Remaining: " + secondsRemaining / 60 + "m " + secondsRemaining % 60 + "s");
            if (secondsRemaining <= 0) {
                timer.stop();
                showLoseScreen();
            }
        });
        timer.start();
    }

    private int getDifficultyLevel(String difficulty) {
        switch (difficulty) {
            case "Easy":
                return 12;
            case "Medium":
                return 16;
            case "Hard":
                return 20;
            default:
                return 16;
        }
    }

    private void showLoseScreen() {
        int option = JOptionPane.showOptionDialog(this, "Time's up! You lose.", "Game Over",
                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, new String[]{"Try Again", "Exit"}, "Try Again");

        if (option == JOptionPane.YES_OPTION) {
            showWelcomeScreen();
        } else {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
