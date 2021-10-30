package com.alexsobiek.jbasic.memory;

public class MemoryBlock {
    private final AddressRange range;
    private final MemoryObject memoryObject;

    public MemoryBlock(AddressRange range, MemoryObject memoryObject) {
        this.range = range;
        this.memoryObject = memoryObject;
    }

    public AddressRange getRange() {
        return range;
    }

    public MemoryObject getMemoryObject() {
        return memoryObject;
    }
}
