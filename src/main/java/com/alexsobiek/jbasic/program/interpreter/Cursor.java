package com.alexsobiek.jbasic.program.interpreter;

import com.alexsobiek.jbasic.API;
import com.alexsobiek.jbasic.event.EventListener;
import com.alexsobiek.jbasic.event.Listener;
import com.alexsobiek.jbasic.event.events.CPUCycle;
import com.alexsobiek.jbasic.event.events.KeyInputEvent;

public class Cursor implements Listener {
    private int x = 0, y = 0;
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
            api.getWindow().setCharColor(x, y, api.getWindow().getBackgroundColor(), api.getWindow().getForegroundColor());
        } else {
            api.getWindow().setCharColor(x, y, api.getWindow().getForegroundColor(), api.getWindow().getBackgroundColor());
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
                y += 1;
            case 37:
                y -= 1;

        }
    }
}
