package com.alexsobiek.jbasic.io;

import com.alexsobiek.jbasic.event.EventBus;
import com.alexsobiek.jbasic.event.events.KeyInputEvent;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Keyboard {
    public Keyboard(EventBus eventBus) {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(event -> {
            switch (event.getID()) {
                case KeyEvent.KEY_PRESSED:
                    eventBus.post(new KeyInputEvent.Pressed(event.getKeyChar(), event.getKeyCode()));
                    break;
                case KeyEvent.KEY_RELEASED:
                    eventBus.post(new KeyInputEvent.Released(event.getKeyChar(), event.getKeyCode()));
                    break;
            }
            return true;
        });
    }
}
