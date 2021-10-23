package com.alexsobiek.jbasic;

import com.alexsobiek.jbasic.event.EventBus;
import com.alexsobiek.jbasic.graphics.Window;
import com.alexsobiek.jbasic.io.IOHandler;

/**
 * JBasic - A BASIC interpreter and REPL environment
 * Written by Alexander Sobiek, 2021
 */

public class Main {
    /**
     * Main entry point for JBasic
     * @param args Startup arguments
     */
    public static void main(String[] args) {
        /**
         * The reason we call a secondary "main" class is because we need the main class to implement API and those
         * methods are more preferable to not be static as the rest of the program will not contain static objects.
         *
         * TODO: Add startup arguments for columns and rows and pass to JMain.window
         */
        new JMain();
    }
}

class JMain implements API {
    private final EventBus eventBus;    // Event bus handles posting, subscribing, and unsubscribing from events
    private final Window window;        // Main Window (JPanel) class
    private final IOHandler io;         // Main IO class

    /**
     * Constructor for the main instance of JBasic
     */
    public JMain() {
        eventBus = new EventBus();
        window = new Window(40, 25);    // Creates new 40x25 column Window
        io = new IOHandler(eventBus);
    }

    public void loadProgram(Program program) {
        program.onLoad(this);
    }

    /**
     * Gets the Event Bus class
     * @return EventBus
     */
    @Override
    public EventBus getEventBus() {
        return eventBus;
    }

    /**
     * Gets the Window class
     * @return Window
     */
    @Override
    public Window getWindow() {
        return window;
    }

    /**
     * Gets the IO handler class
     * @return return IOHandler
     */
    @Override
    public IOHandler getIO() {
        return io;
    }
}
