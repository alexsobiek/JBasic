package com.alexsobiek.jbasic.io;

import com.alexsobiek.jbasic.event.EventBus;

public class IOHandler {
    public IOHandler(EventBus eventBus) {
        new Keyboard(eventBus);
    }
}
