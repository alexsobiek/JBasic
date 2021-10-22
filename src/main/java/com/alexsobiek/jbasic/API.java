package com.alexsobiek.jbasic;

import com.alexsobiek.jbasic.event.EventBus;
import com.alexsobiek.jbasic.graphics.Window;
import com.alexsobiek.jbasic.io.IOHandler;

/**
 * Simple interface for regulating which methods from JMain are accessible outside that class
 */
public interface API {
    EventBus getEventBus();
    Window getWindow();
    IOHandler getIO();
}
