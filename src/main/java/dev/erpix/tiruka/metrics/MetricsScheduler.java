package dev.erpix.tiruka.metrics;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class MetricsScheduler {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

}
