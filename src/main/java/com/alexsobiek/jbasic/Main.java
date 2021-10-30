package com.alexsobiek.jbasic;

import com.alexsobiek.jbasic.event.EventBus;
import com.alexsobiek.jbasic.event.EventListener;
import com.alexsobiek.jbasic.event.Listener;
import com.alexsobiek.jbasic.event.events.ProcessorCycle;
import com.alexsobiek.jbasic.graphics.ScreenMemory;
import com.alexsobiek.jbasic.graphics.Window;
import com.alexsobiek.jbasic.io.IOHandler;
import com.alexsobiek.jbasic.memory.MemoryBus;
import com.alexsobiek.jbasic.program.interpreter.BasicInterpreter;

/**
 * JBasic - A BASIC interpreter and REPL environment
 * Written by Alexander Sobiek, 2021
 */

public class Main {
    /**
     * Main entry point for JBasic
     *
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

class JMain implements API, Listener {
    private final MemoryBus memoryBus;
    private final EventBus eventBus;    // Event bus handles posting, subscribing, and unsubscribing from events
    private final Window window;        // Main Window (JPanel) class
    private final IOHandler io;         // Main IO class

    /**
     * Constructor for the main instance of JBasic
     */
    public JMain() {
        memoryBus = new MemoryBus();
        setupMemory();
        eventBus = new EventBus();
        window = new Window(memoryBus);
        window.writeString(0, 0, "Loading Program...");
        io = new IOHandler(eventBus);
        new Clock(eventBus);
        eventBus.subscribe(this);
        window.clearScreen();
        loadProgram(new BasicInterpreter());
    }

    /**
     * Adds all the "system" memory to the memory bus to be addressed
     */
    private void setupMemory() {
        memoryBus.add(new ScreenMemory(40, 25));
    }

    public void loadProgram(Program program) {
        program.onLoad(this);
    }

    @EventListener
    public void onCPUCycle(ProcessorCycle cycle) {
        window.paint(window.getGraphics());
    }

    /**
     * Returns the Memory Bus Object
     *
     * @return MemoryBus
     */
    public MemoryBus getMemoryBus() {
        return memoryBus;
    }

    /**
     * Gets the Event Bus Object
     *
     * @return EventBus
     */
    @Override
    public EventBus getEventBus() {
        return eventBus;
    }

    /**
     * Gets the Window class
     *
     * @return Window
     */
    @Override
    public Window getWindow() {
        return window;
    }

    /**
     * Gets the IO handler class
     *
     * @return return IOHandler
     */
    @Override
    public IOHandler getIO() {
        return io;
    }
}
