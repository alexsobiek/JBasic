package com.alexsobiek.jbasic.event.events;

import com.alexsobiek.jbasic.event.Event;

public class KeyInputEvent extends Event {
    private final char key;
    private final int code;

    public KeyInputEvent(char key, int code) {
        this.key = key;
        this.code = code;
    }

    public char getKey() {
        return key;
    }

    public int getCode() {
        return code;
    }

    public static class Pressed extends KeyInputEvent {
        public Pressed(char key, int code) {
            super(key, code);
        }
    }

    public static class Released extends KeyInputEvent {
        public Released(char key, int code) {
            super(key, code);
        }
    }
}
