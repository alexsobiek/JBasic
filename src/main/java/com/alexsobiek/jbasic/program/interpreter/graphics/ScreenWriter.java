package com.alexsobiek.jbasic.program.interpreter.graphics;

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

    /**
     * Triggers a cursor move in the provided direction
     * @param direction Direction to move
     */
    private void moveCursor(Cursor.Direction direction) {
        cursor.move(direction);
        cursor.triggerMove();
    }

    /**
     * Modifies and writes the character
     * @param character Character to modify/write
     * @param c new char to give to Character
     */
    private void writeChar(Character character, char c) {
        character.setChar(c);
        api.getWindow().writeCharacter(character);
        moveCursor(Cursor.Direction.RIGHT);
    }

    @EventListener(priority = 0)
    public void onKey(KeyInputEvent.Pressed event) {
        Character character;
        if (event.getCode() == 8) {                         // Backspace
            moveCursor(Cursor.Direction.LEFT);
            character = cursor.getSelected();
            character.setChar(' ');
            api.getWindow().writeCharacter(character);
        } else {
            char c = event.getKey();
            character = cursor.getSelected();
            if (c > 31 && c < 127) {                        // Readable ASCII characters
                writeChar(character, c);
            } else {
                switch(event.getCode()) {
                    case 10:                                // Return
                        moveCursor(Cursor.Direction.DOWN);
                        break;
                    case 127:                               // Delete
                        writeChar(character,  ' ');
                        break;
                }
            }
        }
    }
}
