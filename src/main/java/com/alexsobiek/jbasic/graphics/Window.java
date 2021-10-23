package com.alexsobiek.jbasic.graphics;

import com.alexsobiek.jbasic.memory.ScreenMemory;

import javax.swing.*;
import java.awt.*;

// TODO: Add support for different column/line combinations, not just 40x25
public class Window extends JPanel {

    private final ScreenMemory memory;
    private final int columns;
    private final int lines;
    private final Color color;

    public Window(int columns, int lines) {
        memory = new ScreenMemory(columns, lines);
        this.columns = columns;
        this.lines = lines;
        color = new Color();
        JFrame frame = new JFrame("JBasic");
        frame.setSize(640, 480); // VGA Resolution
        frame.add(this);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setBackground(java.awt.Color.BLACK);
    }


    /**
     * Writes a string starting at the provided line and column with the default foreground and background colors
     * @param line Starting line
     * @param column Starting column
     * @param string String to write
     */
    public void writeString(int line, int column, String string) {
        writeString(line, column, string, 255, 0);
    }

    /**
     * Writes a string starting at the provided line and column with the foreground and background colors
     * @param line Starting line
     * @param column Starting column
     * @param string String to write
     * @param foreground Foreground color
     * @param background Background color
     */
    public void writeString(int line, int column, String string, int foreground, int background) {
        char[] chars = string.toCharArray();
        for (int i = 0; i < chars.length; i++) writeChar(line, column+i, chars[i], foreground, background);
    }

    /**
     * Writes a Character with the default foreground and background colors at the screen position
     * @param line Line number
     * @param column Column number
     * @param character Character to write
     */
    public void writeChar(int line, int column, char character) {
        writeChar(line, column, character, 255, 0);
    }

    /**
     * Writes a Character with the foreground and background at the screen position
     * @param line Line number
     * @param column Column number
     * @param character Character to write
     * @param foreground Foreground color
     * @param background Background color
     */
    public void writeChar(int line, int column, char character, int foreground, int background) {
        if (line < lines && column < columns) {
            int address = (120*line) + (3*column);
            memory.poke((short)address, (byte)character);
            memory.poke((short)(address+1), (byte)foreground);
            memory.poke((short)(address+2), (byte)background);
        }
    }

    /**
     * Gets the character bytes for the provided row and column. Note: row and column start at 0
     * @param line Row number
     * @param column Column number
     * @return byte[3]: character, foreground, background
     */
    private byte[] getCharacterBytes(int line, int column) {
        // Video memory lives between 0x80 and 0xC50
        // Each row occupies 75 bytes - character, foreground, background
        byte[] b = new byte[3];
        int address = (120*line) + (3*column); // each column has 3 bytes, 40 columns, 25 lines
        for (int i = 0; i < 3; i++) {
            b[i] = memory.peek((short)(address+i));
            // System.out.println((short)(address+i));
        }
        return b;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        for (int i = 0; i < lines; i++) {                   // loop through all lines
            for (int j = 0; j < columns; j++) {             // loop through each column on each line
                byte[] charMem = getCharacterBytes(i, j);
                if (charMem[0] != 0x00) {
                    String s = String.valueOf((char)charMem[0]);
                    int x = j * 16;       // Each new character moves over by 16px
                    int y = (i + 1) * 18;   // Each new character moves down by 18px
                    g2d.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14)); // Set the font size

                    // Background rectangle
                    g2d.setColor(color.from(charMem[2]));
                    g.fillRect(x, y - 16, 16, 18);

                    // Draw Character
                    g2d.setColor(color.from(charMem[1]));

                    /*
                    x coordinate calculation explanation:
                        each character has a width of 16px, so we subtract the width of the character from 16
                        to get the remaining pixels, then divide by two to calculate the pixels on each side,
                        and add to our x integer which is the starting point for this character.
                    */
                    g2d.drawString(s, ((16 - g2d.getFontMetrics().stringWidth(s)) / 2) + x, y - 1);

                }
            }
        }
    }
}
