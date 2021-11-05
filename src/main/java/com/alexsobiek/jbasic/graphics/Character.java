package com.alexsobiek.jbasic.graphics;

public class Character {
    private char c;
    private short foreground;
    private short background;
    private int line;
    private int column;

    public Character(char c, short foreground, short background, int line, int column) {
        this(c, foreground, background);
        this.line = line;
        this.column = column;
    }

    public Character(char c, short foreground, short background) {
        this.c = c;
        this.foreground = foreground;
        this.background = background;
    }

    public void setChar(char c) {
        this.c = c;
    }

    public char getChar() {
        return c;
    }

    public void setForeground(short foreground) {
        this.foreground = foreground;
    }

    public short getForeground() {
        return foreground;
    }

    public void setBackground(short background) {
        this.background = background;
    }

    public short getBackground() {
        return background;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }
}
