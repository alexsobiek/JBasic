package com.alexsobiek.jbasic.memory;

import java.util.HashMap;
import java.util.Map;

public class MemoryBus {
    private final Map<AddressRange, MemoryObject> memoryBlocks;
    private short lastAddress;

    /**
     * Constructor
     */
    public MemoryBus() {
        memoryBlocks = new HashMap<>();
        lastAddress = 0;
    }

    /**
     * Adds and addresses a memory object in this memory bus
     * @param memory MemoryObject to add to this bus
     * @return AddressRange
     */
    public AddressRange add(MemoryObject memory) {
        short endingAddress = (short) (lastAddress + memory.size());
        AddressRange range = new AddressRange(lastAddress, endingAddress);
        memoryBlocks.put(range, memory);
        lastAddress = endingAddress;
        return range;
    }

    /**
     * Returns a MemoryObject if the provided address exists
     * @param address Address to get
     * @return MemoryObject
     */
    public MemoryBlock get(short address) {
        MemoryBlock b = null;
        for (Map.Entry<AddressRange, MemoryObject> block : memoryBlocks.entrySet())
            if (block.getKey().contains(address)) b = new MemoryBlock(block.getKey(), block.getValue());
        return b;
    }

    /**
     * Calculates the memory address of the block from the address of all memory
     * @param address Memory address
     * @return short - address for the block
     */
    private short calculateBlockAddress(short address) {
        return (short) (address - get(address).getRange().getStart());
    }

    /**
     * Returns the value at the provided memory address
     * @param address Address to peek
     * @return byte
     */
    public byte peek(short address) {
        byte b = 0x00;
        MemoryBlock block = get(address);
        if (block != null) b = ((ROM) block.getMemoryObject()).peek(calculateBlockAddress(address));
        return b;
    }

    /**
     * Pokes byte into the provided address IFF it is RAM
     * @param address Memory address
     * @param value Byte value
     * @throws ROMInsertionException Thrown when the memory object at the provided address is read-only
     */
    public void poke(short address, byte value) throws ROMInsertionException {
        MemoryBlock block = get(address);
        MemoryObject o = block.getMemoryObject();
        if (o instanceof RAM) ((RAM) o).poke(calculateBlockAddress(address), value);
        else throw new ROMInsertionException(String.format("Attempted to insert byte into ROM at %d", address));
    }

}
