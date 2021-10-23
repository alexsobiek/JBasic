package com.alexsobiek.jbasic.program.interpreter;

public class BLine {
    private final int line;
    private final String code;

    public int getLine() {
        return line;
    }

    public String getCode() {
        return code;
    }

    public BLine(int line, String code) {
        this.line = line;
        this.code = code;
    }
}
