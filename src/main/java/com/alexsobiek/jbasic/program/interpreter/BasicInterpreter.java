package com.alexsobiek.jbasic.program.interpreter;

import com.alexsobiek.jbasic.API;
import com.alexsobiek.jbasic.Program;

public class BasicInterpreter implements Program {
    @Override
    public void onLoad(API api) {
        api.getWindow().writeString(0, 0, "BASIC Interpreter");
        Cursor cursor = new Cursor(api);
        api.getEventBus().subscribe(cursor);
        api.getEventBus().subscribe(new ScreenWriter(api, cursor));
    }
}
