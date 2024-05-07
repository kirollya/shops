package pers.nico.metrics;

import io.micrometer.core.instrument.config.MeterFilter;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.quarkus.micrometer.runtime.MeterFilterConstraint;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Produces;

@ApplicationScoped
public class MetricsManager {

    @Produces
    @ApplicationScoped
    @MeterFilterConstraint(applyTo = PrometheusMeterRegistry.class)
    public MeterFilter filterApplicationMetrics() {
        return MeterFilter.accept();
    }

}
