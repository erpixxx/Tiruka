package dev.erpix.tiruka.metrics;

import com.sun.management.OperatingSystemMXBean;
import io.prometheus.client.Gauge;

import java.lang.management.ManagementFactory;

public class MetricsCollector {

    private static final OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

    private static final Gauge cpuUsage = Gauge.build()
            .name("cpu_usage")
            .help("Current CPU Usage")
            .register();

    private static final Gauge memoryUsage = Gauge.build()
            .name("app_memory_usage_bytes")
            .help("Current Memory Usage")
            .register();

    public static void updateMetrics() {
        cpuUsage.set(osBean.getCpuLoad() * 100);
        memoryUsage.set(osBean.getFreeMemorySize());
    }

}
