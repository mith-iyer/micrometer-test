package com.miyer.testmicrometer.config.metrics;

import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@RequiredArgsConstructor
public class StatusCustomMetric implements MeterBinder {
    private static final String METRIC_NAME = "app_status";
    private static final String STATUS = "status";
    private static final String METRIC_DESCRIPTION = "Status of the testMicrometer app.";
    private static final double STATUS_UP = 1.0;
    private static final double STATUS_DOWN = 0.0;
    private final HealthEndpoint healthEndpoint;

    @Override
    public void bindTo(@NonNull MeterRegistry registry) {
        Gauge.builder(METRIC_NAME, this, StatusCustomMetric::status).description(METRIC_DESCRIPTION)
            .baseUnit(STATUS).register(registry);
    }

    /**
     * 1.0 when application is up, 0.0 when is down
     */
    protected double status() {
        double result = STATUS_DOWN;
        try {
            result = Status.UP.equals(healthEndpoint.health().getStatus()) ? STATUS_UP : STATUS_DOWN;
        } catch (Exception exception) {
            log.error("", exception);
        }
        return result;
    }

}

