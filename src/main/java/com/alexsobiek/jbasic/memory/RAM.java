package com.alexsobiek.jbasic.memory;

public abstract class RAM extends ROM {
    /**
     * Stores the value in this memory object at the provided address
     * @param address Address to store value at
     * @param value Value to store
     */
    void poke(short address, byte value) {
        if (address < memory.length) memory[address] = value;
    }
}
