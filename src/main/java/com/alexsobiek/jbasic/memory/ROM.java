package com.alexsobiek.jbasic.memory;

public abstract class ROM {
    byte[] memory;

    /**
     * Returns the value in this memory object at the provided address
     * @param address Address to pull byte from
     * @return byte
     */
    public byte peek(short address) {
        return memory[address];
    }
}
