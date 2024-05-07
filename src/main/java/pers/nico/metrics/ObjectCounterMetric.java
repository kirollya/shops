package pers.nico.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ObjectCounterMetric {

    @Inject
    MeterRegistry registry;

    public void putObj(Integer count) {
        this.registry.counter("new objects", "layer", "boundary").increment(count);
    }

    public void putObj() {
        this.putObj(1);
    }

}
