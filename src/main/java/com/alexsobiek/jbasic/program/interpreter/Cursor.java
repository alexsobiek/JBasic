package com.alexsobiek.jbasic.program.interpreter;

import com.alexsobiek.jbasic.API;
import com.alexsobiek.jbasic.event.EventListener;
import com.alexsobiek.jbasic.event.Listener;
import com.alexsobiek.jbasic.event.events.CPUCycle;
import com.alexsobiek.jbasic.event.events.KeyInputEvent;
import com.alexsobiek.jbasic.memory.RAM;

public class Cursor implements Listener {
    private int line = 0, column = 0;
    private byte cycle = 0;

    private final API api;

    public Cursor(API api) {
        this.api = api;
    }

    @EventListener
    public void onTick(CPUCycle event) {
        cycle++;
        if (cycle == 2) {
            cycle = 0;
            api.getWindow().setCharColor(line, column, api.getWindow().getBackgroundColor(), api.getWindow().getForegroundColor());
        } else {
            api.getWindow().setCharColor(line, column, api.getWindow().getForegroundColor(), api.getWindow().getBackgroundColor());
        }
    }

    @EventListener
    public void onKey(KeyInputEvent.Pressed event) {
        // 39 -> right
        // 37 -> left
        // 40 -> down
        // 38 -> up
        switch (event.getCode()) {
            case 39:
                if (column < api.getWindow().getColumns()) column += 1;
                break;
            case 37:
                if (column > 0) column -= 1;
                break;
        }
    }
}
