package com.alexsobiek.jbasic;

import com.alexsobiek.jbasic.event.EventBus;
import com.alexsobiek.jbasic.event.events.CPUCycle;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Clock {
    public Clock(EventBus eventBus) {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        CPUCycle cycle = new CPUCycle();
        service.scheduleAtFixedRate(() -> eventBus.post(cycle), 0, 500, TimeUnit.MILLISECONDS);
    }
}
