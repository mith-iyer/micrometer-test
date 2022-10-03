package com.miyer.testmicrometer.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import com.miyer.testmicrometer.controller.PersonController;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.jersey.server.DefaultJerseyTagsProvider;
import io.micrometer.core.instrument.binder.jersey.server.MetricsApplicationEventListener;

@Configuration
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig(MeterRegistry meterRegistry) {
        register(PersonController.class);
        register(new MetricsApplicationEventListener(meterRegistry, new DefaultJerseyTagsProvider(),
            "http.server.requests", true));
    }
}
