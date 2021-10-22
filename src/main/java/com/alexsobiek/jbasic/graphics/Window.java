package com.alexsobiek.jbasic.graphics;

import com.alexsobiek.jbasic.memory.ScreenMemory;

import javax.swing.*;

public class Window extends JPanel {

    private final ScreenMemory memory;
    public Window(int columns, int rows) {
        memory = new ScreenMemory(columns, rows);
    }
}
