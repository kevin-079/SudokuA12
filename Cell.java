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

public class Cell extends JTextField {
    public static final Color BG_GIVEN = new Color(240, 240, 240);
    public static final Color FG_GIVEN = Color.BLACK;
    public static final Color BG_TO_GUESS = Color.YELLOW;
    public static final Font FONT_NUMBERS = new Font("OCR A Extended", Font.PLAIN, 28);

    int row, col;
    int number;
    boolean isGiven;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        setHorizontalAlignment(JTextField.CENTER);
        setFont(FONT_NUMBERS);
    }

    public void newGame(int number, boolean isGiven) {
        this.number = number;
        this.isGiven = isGiven;
        setEditable(!isGiven);
        setBackground(isGiven ? BG_GIVEN : BG_TO_GUESS);
        setText(isGiven ? String.valueOf(number) : "");
    }
}

