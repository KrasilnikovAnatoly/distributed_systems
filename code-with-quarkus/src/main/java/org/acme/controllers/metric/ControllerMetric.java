package org.acme.controllers.metric;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@ApplicationScoped
public class ControllerMetric {
    MeterRegistry meterRegistry = Metrics.globalRegistry;
    AtomicInteger gauge = meterRegistry.gauge("my.gauge", new AtomicInteger(0));

    public void changeMetric() {
        gauge.set(new Random().nextInt(10));
    }
}
