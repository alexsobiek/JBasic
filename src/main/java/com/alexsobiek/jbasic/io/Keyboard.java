package com.alexsobiek.jbasic.io;

import com.alexsobiek.jbasic.Program;
import com.alexsobiek.jbasic.event.EventBus;
import com.alexsobiek.jbasic.event.EventListener;
import com.alexsobiek.jbasic.event.Listener;
import com.alexsobiek.jbasic.event.events.KeyInputEvent;
import com.alexsobiek.jbasic.event.events.ProcessorCycle;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Keyboard implements Listener {
    private final KeyBuffer buffer;
    private final EventBus eventBus;
    public Keyboard(EventBus eventBus) {
        this.buffer = new KeyBuffer();
        this.eventBus = eventBus;
        eventBus.subscribe(this);

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(event -> {
            switch (event.getID()) {
                case KeyEvent.KEY_PRESSED:
                    buffer.push(new KeyInputEvent.Pressed(event.getKeyChar(), event.getKeyCode()));
                    break;
                case KeyEvent.KEY_RELEASED:
                    // buffer.push(new KeyInputEvent.Released(event.getKeyChar(), event.getKeyCode()));
                    break;
            }
            return true;
        });
    }

    @EventListener
    public void onProcessorCycle(ProcessorCycle event) {
        if (buffer.peek() != null) {
            eventBus.post(buffer.pop());
        }
    }

}
class KeyBuffer {
    private Node head;
    private Node current;

    public enum ACTION { CursorMove }

    public KeyBuffer() { }

    public void push(KeyInputEvent key) {
        Node n = new Node(key);
        if (head == null) {
            head = n;
            current = head;
        }
        else {
            current.setNext(n);
            current = n;
        }
        if (head == null) head = n;
    }



    public KeyInputEvent pop() {
        KeyInputEvent removed = head.getKey();
        if (head.hasNext()) head = head.getNext();
        else head = null;
        return removed;
    }

    public KeyInputEvent peek() {
        return (head != null) ? head.getKey() : null;
    }

    class Node {
        private KeyInputEvent key;
        private Node next;

        protected Node(KeyInputEvent key) {
            this.key = key;
        }

        protected KeyInputEvent getKey() {
            return key;
        }

        protected void setKey(KeyInputEvent key) {
            this.key = key;
        }

        protected boolean hasNext() {
            return next != null;
        }

        protected Node getNext() {
            return next;
        }

        protected void setNext(Node next) {
            this.next = next;
        }
    }
}

