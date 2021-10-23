package com.alexsobiek.jbasic.program.interpreter;

import java.util.ArrayList;
import java.util.Comparator;

public class BProgram {
    ArrayList<BLine> lines;

    public BProgram() {
        lines = new ArrayList<>();
    }

    public void addLine(BLine line) {
        lines.add(line);
    }

    public ArrayList<BLine> getLines() {
        lines.sort(new LineSort());
        return lines;
    }
}

class LineSort implements Comparator<BLine> {
    @Override
    public int compare(BLine a, BLine b) {
        int out = 0;
        if (a.getLine() > b.getLine()) out = 1;
        else if (a.getLine() < b.getLine()) out = -1;
        return out;
    }
}
