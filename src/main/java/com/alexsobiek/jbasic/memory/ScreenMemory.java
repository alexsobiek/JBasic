package com.alexsobiek.jbasic.memory;

public class ScreenMemory extends RAM {
    /**
     * Constructs the ScreenMemory class which handles storing text that displays in the Window.
     * @param columns Number of columns the Window class will have
     * @param rows Number of rows the Window class will have
     */
    public ScreenMemory(int columns, int rows) {
        /**
         * The screen memory byte[] consists of 3 bytes per character position
         * Byte 1 - Character
         * Byte 2 - Foreground Color Short
         * Byte 3 - Background Color Short
         */
        memory = new byte[columns*3*rows];
    }
}
