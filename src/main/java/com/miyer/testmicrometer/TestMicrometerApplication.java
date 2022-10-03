package com.miyer.testmicrometer;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

@SpringBootApplication
@ServletComponentScan
@ComponentScan(value = {"com.miyer.testmicrometer.*"})
@EntityScan({"com.miyer.testmicrometer.entity"})
@EnableJpaRepositories({"com.miyer.testmicrometer.repository"})
public class TestMicrometerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestMicrometerApplication.class, args);
	}

	@Bean
	public MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
		return registry -> registry.config().commonTags("host", getHost(), "application", "testMicrometer");
	}

	private String getHost() {
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException exception) {
			return "unknown-host";
		}
	}

	@Bean
    public MeterRegistry getMeterRegistry() {
        return new SimpleMeterRegistry();
    }

}
