package com.alexsobiek.jbasic.memory;

import java.util.Arrays;

public abstract class ROM implements MemoryObject {
    protected byte[] memory;

    /**
     * Returns the size of this memory object
     * @return int
     */
    @Override
    public int size() {
        return memory.length;
    }

    /**
     * Returns the value in this memory object at the provided address
     * @param address Address to pull byte from
     * @return byte
     */
    public byte peek(short address) {
        return memory[address];
    }
}
