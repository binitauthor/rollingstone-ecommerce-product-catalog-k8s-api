package com.rollingstone.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class ProductMetricsConfiguration {

    @Bean
    public Counter createdProductCreationCounter(MeterRegistry registry) {
        return Counter
                .builder("com.rollingstone.product.created")
                .description("Number of Products Created")
                .tags("environment", "production")
                .register(registry);
    }

    @Bean
    public Counter http400ExceptionCounter(MeterRegistry registry) {
        return Counter
                .builder("com.rollingstone.ProductController.HTTP400")
                .description("How many HTTP Bad Request HTTP 400 Requests have been received since start time of this instance.")
                .tags("environment", "production")
                .register(registry);
    }

    @Bean
    public Counter http404ExceptionCounter(MeterRegistry registry) {
        return Counter
                .builder("com.rollingstone.ProductController.HTTP404")
                .description("How many HTTP Resource Not Found HTTP 404 Requests have been received since start time of this instance. ")
                .tags("environment", "production")
                .register(registry);
    }

}
