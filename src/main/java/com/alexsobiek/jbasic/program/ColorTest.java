package com.alexsobiek.jbasic.program;

import com.alexsobiek.jbasic.API;
import com.alexsobiek.jbasic.Program;

/**
 * This "program" creates a 16x16 color demo in the middle of the window
 */
public class ColorTest implements Program {

    @Override
    public void onLoad(API api) {
        int currentColor = 0;
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                api.getWindow().writeChar(4+i, 12+j, ' ', currentColor, currentColor);
                currentColor++;
            }
        }
    }
}
