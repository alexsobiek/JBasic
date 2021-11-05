package com.alexsobiek.jbasic;

import com.alexsobiek.jbasic.event.EventBus;
import com.alexsobiek.jbasic.event.events.ProcessorCycle;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Clock {
    public Clock(EventBus eventBus) {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        ProcessorCycle cycle = new ProcessorCycle();
        service.scheduleAtFixedRate(() -> eventBus.post(cycle), 0, 3, TimeUnit.MILLISECONDS);
    }
}
