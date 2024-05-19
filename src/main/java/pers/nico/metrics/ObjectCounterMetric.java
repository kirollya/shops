package pers.nico.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ObjectCounterMetric {

    @Inject
    MeterRegistry registry;

    private List<Integer> values = new ArrayList<>();

    public void putObj() {
        if (Math.round((float)Math.random() * 10) > 5)
            values.add(1);
        else
            values.remove(0);
        this.registry.gauge("my_gauge", Tags.of("key", "value"), values, List::size);
        //values.add(Math.round((float)Math.random() * 10));
        //this.registry.gaugeCollectionSize("my_gau.list", Tags.of("key", "value"), values);
    }

    /*public void putObj() {
        this.putObj(1);
    }*/

}
