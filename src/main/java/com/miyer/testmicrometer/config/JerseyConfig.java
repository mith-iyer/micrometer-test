package com.miyer.testmicrometer.config;

import java.time.Duration;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import com.miyer.testmicrometer.controller.PersonController;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.jersey.server.DefaultJerseyTagsProvider;
import io.micrometer.core.instrument.binder.jersey.server.MetricsApplicationEventListener;
import io.micrometer.core.instrument.logging.LoggingMeterRegistry;
import io.micrometer.core.instrument.logging.LoggingRegistryConfig;

@Configuration
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(PersonController.class);
        register(new MetricsApplicationEventListener(getMeterRegistry(), new DefaultJerseyTagsProvider(),
            "http.server.requests", true));
    }

    private MeterRegistry getMeterRegistry() {
        return new LoggingMeterRegistry(new LoggingRegistryConfig() {
            @Override
            public String get(String key) {
                return null;
            }

            @Override
            public Duration step() {
                return Duration.ofSeconds(10);
            }
        }, Clock.SYSTEM);
    }
}
