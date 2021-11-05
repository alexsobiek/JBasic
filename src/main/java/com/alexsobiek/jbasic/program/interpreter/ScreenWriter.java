package com.alexsobiek.jbasic.program.interpreter;

import com.alexsobiek.jbasic.API;
import com.alexsobiek.jbasic.event.EventListener;
import com.alexsobiek.jbasic.event.Listener;
import com.alexsobiek.jbasic.event.events.KeyInputEvent;
import com.alexsobiek.jbasic.graphics.Character;

public class ScreenWriter implements Listener {
    private final API api;
    private final Cursor cursor;
    public ScreenWriter(API api, Cursor cursor) {
        this.api = api;
        this.cursor = cursor;
    }

    @EventListener
    public void onKey(KeyInputEvent.Pressed event) {
        char c = event.getKey();
        if (c > 31 && c < 127) {
            Character character = cursor.getSelected();
            character.setChar(c);
            api.getWindow().writeCharacter(character);
        }
    }

}
