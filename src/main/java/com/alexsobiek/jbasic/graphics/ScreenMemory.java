package com.alexsobiek.jbasic.graphics;

import com.alexsobiek.jbasic.memory.RAM;

public class ScreenMemory extends RAM {
    /**
     * Constructs the ScreenMemory class which handles storing text that displays in the Window.
     * @param columns Number of columns the Window class will have
     * @param lines Number of rows the Window class will have
     */
    public ScreenMemory(int columns, int lines) {
        /**
         * The screen memory byte[] consists of 3 bytes per character position
         * Byte 1 - Character
         * Byte 2 - Foreground Color Short
         * Byte 3 - Background Color Short
         */
        memory = new byte[6+columns*3*lines];   // Create new memory byte[]
        poke((short)0, (byte)lines);            // Store lines in memory
        poke((short)1, (byte)columns);          // Store columns in memory
    }
}
