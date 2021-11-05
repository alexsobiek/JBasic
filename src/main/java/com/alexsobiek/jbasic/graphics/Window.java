package com.alexsobiek.jbasic.graphics;

import com.alexsobiek.jbasic.memory.MemoryBus;

import javax.swing.*;
import java.awt.*;

// TODO: Add support for different column/line combinations, not just 40x25
public class Window extends JPanel {
    private final ScreenMemory memory;
    private final int lines;
    private final int columns;
    private final Color color;

    public Window(MemoryBus memoryBus) {
        memory = (ScreenMemory) memoryBus.get((short)0).getMemoryObject(); // Get screen memory which starts at 0
        this.lines = memory.peek((short)0);
        this.columns = memory.peek((short)1);
        color = new Color();
        JFrame frame = new JFrame("JBasic");
        frame.setSize(640, 480); // VGA Resolution
        frame.add(this);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        memory.poke((short)2, (byte)0);     // Default Background Color
        memory.poke((short)3, (byte)255);   // Default Foreground Color
        frame.setBackground(color.from(getBackgroundColor()));
    }

    /**
     * Returns the size of the ScreenMemory block.
     * Useful when needing to know the memory position of blocks after ScreenMemory.
     */
    public short getMemorySize() {
        return (short) memory.size();
    }

    /**
     * Returns the amount of columns this Window has
     * @return int
     */
    public int getColumns() {
        return columns;
    }

    /**
     * Returns the amount of lines this Window has
     * @return int
     */
    public int getLines() {
        return lines;
    }

    public int getBackgroundColor() {
        return memory.peek((short)2);
    }

    public int getForegroundColor() {
        return memory.peek((short)3);
    }

    /**
     * Modifies the color scheme of the char at line, column
     * @param line Line number
     * @param column Column number
     * @param foreground Foreground color
     * @param background Background color
     */
    public void setCharColor(int line, int column, int foreground, int background) {
        Character c = getCharAt(line, column);
        writeChar(line, column, c.getChar(), foreground, background);
    }

    /**
     * Writes a string starting at the provided line and column with the default foreground and background colors
     * @param line Starting line
     * @param column Starting column
     * @param string String to write
     */
    public void writeString(int line, int column, String string) {
        writeString(line, column, string, getForegroundColor(), getBackgroundColor());
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
     * Writes a Character from a Character Object
     * @param character Character object
     */
    public void writeCharacter(Character character) {
        writeChar(character.getLine(), character.getColumn(), character.getChar(), character.getForeground(), character.getBackground());
    }

    /**
     * Writes a Character with the default foreground and background colors at the screen position
     * @param line Line number
     * @param column Column number
     * @param character Character to write
     */
    public void writeChar(int line, int column, char character) {
        writeChar(line, column, character, getForegroundColor(), getBackgroundColor());
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
            memory.poke((short)4, (byte)line);      // Write the last character line position to memory
            memory.poke((short)5, (byte)column);    // Write the last character column position to memory
            int address = 6 + (120*line) + (3*column);
            memory.poke((short)address, (byte)character);       // Write the character to memory
            memory.poke((short)(address+1), (byte)foreground);  // Write the character foreground to memory
            memory.poke((short)(address+2), (byte)background);  // Write the character background to memory
        }
    }



    /**
     * Returns the Character (if it exists) at the given line & column
     * @param line Line number
     * @param column Column number
     * @return Character
     */
    public Character getCharAt(int line, int column) {
        Character c = null;
        if (line < this.lines && column < this.columns) {
            short address = (short) (6 + (120*line) + (3*column));
            c = new Character(
                    (char)memory.peek(address),         // Sets the character
                    memory.peek((short)(address+1)),    // Sets the foreground color
                    memory.peek((short)(address+2)),    // Sets the background color
                    line,
                    column
                    );
        }
        return c;
    }


    /**
     * Returns the line, column position of the last saved char to memory
     * @return byte[]
     */
    public Character getLastCharInput() {
        int line = memory.peek((short)4);
        int column = memory.peek((short)5);
        return getCharAt(line, column);
    }

    /**
     * Clears the screen memory
     */
    public void clearScreen() {
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {
                writeChar(i, j, ' ');
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        for (int i = 0; i < lines; i++) {                   // loop through all lines
            for (int j = 0; j < columns; j++) {             // loop through each column on each line
                int x = j * 16;         // Each new character moves over by 16px
                int y = (i + 1) * 18;   // Each new character moves down by 18px
                Character c = getCharAt(i, j);
                // Background rectangle
                g2d.setColor(color.from(c.getBackground()));
                g.fillRect(x, y - 16, 16, 18);
                // Character Drawing
                if (c.getChar() != 0x00) {
                    String s = String.valueOf(c.getChar());
                    g2d.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14)); // Set the font size
                    g2d.setColor(color.from(c.getForeground()));
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
