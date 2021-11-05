package com.alexsobiek.jbasic.program.interpreter;

import com.alexsobiek.jbasic.API;
import com.alexsobiek.jbasic.event.EventListener;
import com.alexsobiek.jbasic.event.Listener;
import com.alexsobiek.jbasic.event.events.ProcessorCycle;
import com.alexsobiek.jbasic.event.events.KeyInputEvent;
import com.alexsobiek.jbasic.graphics.Character;

public class Cursor implements Listener {
    private int line = 0, column = 0;
    private int lastLine = line, lastColumn = column;
    private byte cycle = 0;
    private Character selected;

    enum Direction { UP, DOWN, LEFT, RIGHT }

    private final API api;

    public Cursor(API api) {
        this.api = api;
        selected = api.getWindow().getCharAt(line, column);
    }

    public Character getSelected() {
        return selected;
    }

    /**
     * Moves the cursor by one in the direction specified
     * @param direction Direction to move
     */
    void move(Direction direction) {
        switch(direction) {
            case UP:
                if (line-1 >= 0) {
                    lastLine = line;
                    line--;
                }
                break;
            case DOWN:
                if (line+1 < api.getWindow().getLines()) {
                    lastLine = line;
                    line++;
                }
                break;
            case LEFT:
                if (column-1 >= 0) {
                    lastColumn = column;
                    column--;
                }
                break;
            case RIGHT:
                if (column+1 < api.getWindow().getColumns()) {
                    lastColumn = column;
                    column++;
                }
                break;
        }
    }

    @EventListener
    public void onTick(ProcessorCycle event) {
        cycle++;
        if (cycle > 50) {
            api.getWindow().setCharColor(line, column, api.getWindow().getForegroundColor(), api.getWindow().getBackgroundColor());
            cycle = 0;
        } else if (cycle > 25) {
            if (lastLine != line || lastColumn != column) {
                api.getWindow().writeCharacter(selected); // Write the original character
                lastLine = line;
                lastColumn = column;
                selected = api.getWindow().getCharAt(line, column);
            }
            api.getWindow().setCharColor(line, column, api.getWindow().getBackgroundColor(), api.getWindow().getForegroundColor());
        }
    }

    @EventListener
    public void onKey(KeyInputEvent.Pressed event) {
        // 39 -> right
        // 37 -> left
        // 40 -> down
        // 38 -> up
        switch (event.getCode()) {
            case 37: move(Direction.LEFT); break;
            case 38: move(Direction.UP); break;
            case 39: move(Direction.RIGHT); break;
            case 40: move(Direction.DOWN); break;
        }
    }
}
