package com.alexsobiek.jbasic.memory;

public class AddressRange {
    private final short start;
    private final short end;

    public AddressRange(short start, short end) {
        this.start = start;
        this.end = end;
    }

    public boolean contains(short address) {
        return (address >= start && address <= end);
    }

    public short getStart() {
        return start;
    }

    public short getEnd() {
        return end;
    }
}
